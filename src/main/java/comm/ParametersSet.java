package comm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.testng.Assert;
import org.testng.Reporter;
import io.restassured.response.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 *	@author	qin.hua   
 *
 *	@date	2017/6/8
 * 
 **/
/*
 * 用于接受场景参数集 包含请求参数和验证参数
 */
public class ParametersSet {
	public Map<String, Object> requestParams; // 请求参数,使用此参数
	public Map<String, Object> verifyParams; // 结果验证的参数，放入此map中

	public ParametersSet() {
		requestParams = new HashMap<String, Object>();
		verifyParams = new HashMap<String, Object>();
	}

	/*
	 * 从给定的参数中分析出request参数和result参数
	 * 
	 * @param providerParams: 从dataProvider中给出的参数
	 */
	public void parseProviderParams(Map<String, String> providerParams) {
		for (Map.Entry<String, String> entry : providerParams.entrySet()) {
			// 排除验证结果的参数进入params中
			switch (entry.getKey().toString()) {
			case CONST.URL:
				Assert.assertNotNull(entry.getKey(), "表格中url栏获取的内容结果不能为null，map中没key值也就没value \t");
				Reporter.log("url: " + entry.getValue());
			case CONST.TEST_NAME:
			case CONST.SQL_CHECK:
			case CONST.SQL:
			case CONST.SQL_UPDATE:
			case CONST.SCHEMA:
				verifyParams.put(entry.getKey(), entry.getValue());
				break;
			case CONST.EXCEPTED_RESULT:
				if(entry.getValue().toString().equals("")){
					verifyParams.put(entry.getKey(), "");	
				}
				else{
//					System.out.println("从excel中获得result-json: " + JSONObject.fromObject(entry.getValue()).toString());
					if(judgeJsonFormat(entry.getValue())){
						verifyParams.put(entry.getKey(), JSONObject.fromObject(entry.getValue()));	
					}
					else{
						verifyParams.put(entry.getKey(), entry.getValue().toString());
					}
				}
				break;
			default:
				requestParams.put(entry.getKey(), entry.getValue());
				break;
			}
		}

		Reporter.log("请求参数: " + requestParams.toString());
		
//		Reporter.log("结果参数: " + verifyParams.toString());
	}
	
	/**
	 * 处理下载图片类的接口，该接口返回的是图片的内容
	 * @param response
	 * @param expectResult
	 */
	public void parseImageResult(Response response, Object expectResult){
		//判断返回的response是否是json格式
		if(false == judgeJsonFormat(response.asString())){
			//不是json，是否预期返回的是图片
			if(!expectResult.toString().equals("")){
				if(JSONObject.fromObject(expectResult).get(CONST.EXCEPTED_RESULT).toString().equals(CONST.IMAGE)){
					Assert.assertTrue(judgeImageFile(response.body().asInputStream()), "返回的不是图片\t");
				}
			}
			else{
				Assert.assertTrue(response.getStatusCode()!=200, "预期返回失败，实际返回成功\t");
			}
		}
		else{//走正常判断
			parseResult(response, expectResult);	
		}
		
	}
	
	/**
	 *  解析结果不判断statuCode（直接对比实际返回结果和 excel读取后解析传入的预期结果）
	 * @param response
	 * @param expectResult
	 */
	public void parseResultWithoutStatuCode(Response response, Object expectResult){
//		Reporter.log("reponse: " + response.body().asString());
		//判断返回的response是否是json格式
//		Assert.assertTrue(judgeJsonFormat(response.asString()), "返回的结果不是json格式\t");
		
		Reporter.log("预期返回: " + expectResult.toString());
		JSONObject joExpect = JSONObject.fromObject(expectResult);
		JSONObject joReal = JSONObject.fromObject(response.body().asString());
		
		parseVerifyParams(joExpect, joReal);
	}
	
	/*
	 * 判断返回Response(code)统一接口； 
	 * @param	response: http请求的返回的 Response对象
	 * 			expectResult: 预期返回的JSON
	 * 
	 * 
	 * @author jokin
	 */
	public void parseResult(Response response, Object expectResult){
//		Reporter.log("reponse: " + response.body().asString());		
		//判断使用restAssured 获取 response的statucode状态，并判断该返回状态是否是200
		Assert.assertEquals(response.getStatusCode(), 200, "response返回的statuCode不是200\t");
		
/*		//判断返回的response是否是json格式
		Assert.assertTrue(judgeJsonFormat(response.asString()), "返回的结果不是json格式\t");
		
		Reporter.log("预期返回: " + expectResult.toString());
		JSONObject joExpect = JSONObject.fromObject(expectResult);
		JSONObject joReal = JSONObject.fromObject(response.body().asString());
		
		parseVerifyParams(joExpect, joReal);*/
		
		if(false == judgeJsonFormat(response.asString())){
		Assert.assertEquals(response.asString(), expectResult.toString(), "返回的response不是json格式，且与预期结果不一致\t");
		return;
		}
	
		
		if(expectResult.toString().equals("")){							//预期 无返回值(失败)
			
			/** 临时添加:
			 * 	当返回中没有code=404项目，暂时研发那边会返回一个status:404
			 * 	因此修改，如果没有code项目，则验证status=404项
			 */
			String returnCode = "";
			if(response.body().jsonPath().get("code") == null){					//实际返回没有code项，改判断status项目
				//response.then()
				Assert.assertNotNull(response.body().jsonPath().get("status"), "response返回中没有code项也没有status项 \t");
				returnCode = response.body().jsonPath().get("status").toString();
				Reporter.log("当前没有code项，改判断status的值:" + returnCode);
			}
			else{
				returnCode = response.body().jsonPath().get("code").toString();
			}
			Assert.assertNotEquals(returnCode, "200", "预期返回失败,实际返回成功\t");	
		}
		else{																	//预期返回成功
			Reporter.log("预期返回: " + expectResult.toString());
			JSONObject joExpect = JSONObject.fromObject(expectResult);
			JSONObject joReal = JSONObject.fromObject(response.body().asString());
			
			parseVerifyParams(joExpect, joReal);	
		}
	}
	
	/*
	 * 判断返回Response(JSON)统一接口
	 * @param	joExpect: 预期返回的JSON 预期结果
	 *          joReal: http请求的返回JSON实际结果
	 * 
	 * @author qin.hua
	 */
	private void parseVerifyParams(JSONObject joExpect, JSONObject joReal){
		Iterator<?> it = joExpect.keySet().iterator();
		Iterator<?> itReal = joReal.keySet().iterator();
//		System.out.printf("parseVerifyParams() \n\t获得exceptJson:%s \n\t获得realJson: %s\n", joExpect.toString(), joReal.toString());
		if(!it.hasNext()){		//此时预期此json路径没有内容
			if(it.hasNext()){
				Assert.assertFalse(itReal.hasNext(), "预期" + itReal.next().toString() + "没有数据， 实际它仍有数据\t");	
			}
			Reporter.log("当前预期与实际结果都是空: " + it.toString());
			return;
		}
		Assert.assertTrue(itReal.hasNext(), "返回json为空");
		
		while(it.hasNext()){
			
			String key = it.next().toString();
			Object valueExpect = joExpect.get(key);
			Object valueReal = joReal.get(key);
			
			Assert.assertNotNull(valueReal, "返回json没有此项:" + key + "\t");
			if(valueExpect instanceof JSONObject){
				Assert.assertTrue(valueReal instanceof JSONObject, "解析json类型不一致:" + key + "\t");
				parseVerifyParams(joExpect.getJSONObject(key), joReal.getJSONObject(key));
			}
			else if(valueExpect instanceof JSONArray){
				Assert.assertTrue(valueReal instanceof JSONArray, "解析json类型不一致:" + key + "\t");
				parseJsonArray(joExpect.getJSONArray(key), joReal.getJSONArray(key));
			}
			else {
				//判断预期结果等于 实际结果：如果不等于抛出错误
				Assert.assertTrue(valueExpect.toString().equals(valueReal.toString()), 
								key + " 预期值:" + valueExpect.toString() + " 与实际值:" + valueReal.toString() + "不符合\t");
			}
		}
	}
	
	private void parseJsonArray(JSONArray jaExcept, JSONArray jaReal){
		for(int i = 0; i < jaExcept.size(); i++){
			Object valueExpect = jaExcept.get(i);
			Object valueReal = jaReal.get(i);
			Assert.assertNotNull(valueReal, "返回json没有此项\t");
			
			if(valueExpect instanceof JSONArray){
				Assert.assertTrue(valueReal instanceof JSONArray, "解析json类型不一致\t");
				parseJsonArray(jaExcept.getJSONArray(i), jaReal.getJSONArray(i));
			}
			else if(valueExpect instanceof JSONObject){
				Assert.assertTrue(valueReal instanceof JSONObject, "解析json类型不一致\t");
				parseVerifyParams(jaExcept.getJSONObject(i), jaReal.getJSONObject(i));
			}
			else{
				Assert.assertTrue(valueExpect.toString().equals(valueReal.toString()), 
						"预期值:" + valueExpect.toString() + " 与实际值:" + valueReal.toString() + "不符合\t");
			}
		}
	}
	
	/**
	 * 判断返回的是否是json格式
	 * @param jsonStr
	 * @return
	 * @author qin.hua
	 */
	private boolean judgeJsonFormat(String jsonStr){
		try{
			JSONObject.fromObject(jsonStr);
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	
	/**
	 * 分析返回的是否是image文件
	 * @param fileStream
	 * @author qin.hua
	 */
	private Boolean judgeImageFile(InputStream fileStream){
		//验证返回是否是image格式
		ImageInputStream  iis = null;
		Boolean retCode = false;
		try {
			iis = ImageIO.createImageInputStream(fileStream);
			if(iis == null){
				return retCode;
			}
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if(iter.hasNext()){  
				String formatName = iter.next().getFormatName();
				Reporter.log("返回图片类型为: " + formatName);
				retCode = true;
            }  
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(iis != null){
				try{
					iis.close();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return retCode;
	}
}

