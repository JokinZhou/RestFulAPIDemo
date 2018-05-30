package comm;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import net.sf.json.JSONObject;


/**
 * @author qin.hua
 * 
 * @date 2017年6月15日
 * 
 * @description		B端登录类
 */
public class Login {
	private static RestClient driver = new RestClient();
	
//	private static Headers superVisorHeaders = null;
	
	private static Headers cHeaders = null;				//C端登录得到的headers
	private static String cOpenId = null;
	private static Headers bHeaders = null;				//B端登录得到的headers
	private static Map<String, Object> bLoginInfo = null;			//B端登录得到的返回信息

	
	public static synchronized Map<String, Object> getBLoginInfo(){
		if(bHeaders == null){
			getBHeaders();
		}
		
		return bLoginInfo;
	}
	
	/*
	 * @return B端登陆后获取的sessionId
	 */
	@SuppressWarnings("unchecked")
	public static synchronized Headers getBHeaders(){
		if(bHeaders == null){
			String bLoginUrl = "http://api-partner.uat1.rs.com/settle/app/login";
			String appId = "c3";
			String appSecret = "s333";
			String username = "17000000000";
			String password = "0npQAR0vTFPgPo31a4Hy5g==";
			
			Map<String, Object> loginParams = new HashMap<>();
			loginParams.put("appId", appId);
			loginParams.put("appSecret", appSecret);
			loginParams.put("mobile", username);
			loginParams.put("password", password);
			
			Response responseB = driver.httpPost(bLoginUrl, loginParams);
			System.out.println("获取到B端登录信息: " + responseB.body().asString());
			Assert.assertEquals(responseB.body().jsonPath().get("code").toString(), "200");
			
			bLoginInfo = (Map<String, Object>)(JSONObject.fromObject(responseB.body().path("dataMap")));
	
			String token = responseB.getHeader("x-auth-token");
			Header header = new Header("x-auth-token", token);
			System.out.println("获取token: " + token);
			bHeaders = new Headers(header);
		}
		Assert.assertNotNull(bHeaders, "B端登录失败\t");	
		return bHeaders;
	}
	
/*	public static Headers getBHeaders(){
		synchronized (new Object()){
			if(bHeaders == null){
				String bLoginUrl = "http://api-partner.uat1.rs.com/login/";
				String appId = "c2";
				String appSecret = "s222";
				String imageCode = "000000";
				String username = "17500000008";
				String password = "WtOeyvX2u+CvZ+pT6Fn6FA==";
				
				Map<String, Object> loginParams = new HashMap<>();
				loginParams.put("appId", appId);
				loginParams.put("appSecret", appSecret);
				loginParams.put("imageCode", imageCode);
				loginParams.put("mobileOrUserNameOrEmail", username);
				loginParams.put("password", password);
				
				Response responseB = driver.httpPost(bLoginUrl, loginParams);
				System.out.println("获取到B端登录信息: " + responseB.body().asString());
				Assert.assertEquals(responseB.body().jsonPath().get("code").toString(), "200");
				
				
				String token = responseB.path("dataMap.sessionId");
				Header header = new Header("x-auth-token", token);
				bHeaders = new Headers(header);
			}
			Assert.assertNotNull(bHeaders, "B端登录失败\t");	
		}
		return bHeaders;
	}
	*/
	
	/**
	 * 
	 * @return C端登陆后获取的sessionId和openId
	 */
	public static Headers getCHeaders(){
		synchronized (new Object()){
			if(cHeaders == null){
				//设置登录信息
				String cLoginUrl = "http://api-user.uat1.rs.com/api/user/authenticate";
				String username = "15021874195";
				String password = "8e4ca7af923595919610e17f0dde5140";
				
				Map<String, Object> loginParams = new HashMap<>();
				loginParams.put("appId", "c3");
				loginParams.put("appVersion", "1");
				loginParams.put("captcha", "");
				loginParams.put("username", username);
				loginParams.put("password", password);
				
				//登录请求
				Response responseC = driver.httpPost(cLoginUrl, loginParams);
				System.out.println("获取到C端登录信息: " + responseC.body().asString());
//				Assert.assertEquals(responseC.body().jsonPath().get("code").toString(), "200");
				
				//获取header
				String token = responseC.path("dataMap.sessionid");
				cOpenId = responseC.path("dataMap.openid");
				Header header = new Header("x-auth-token", token);
				cHeaders = new Headers(header);
				System.out.println("已找到C端登录的openId: " + cOpenId);
			}
//			Assert.assertNotNull(cHeaders, "C端登录失败\t");	
		}
		return cHeaders;
	}
	
	public static String getOpenIdForC(){
		getCHeaders();
		return cOpenId;
	}
}
