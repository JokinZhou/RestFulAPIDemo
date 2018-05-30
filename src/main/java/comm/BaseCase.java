package comm;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import util.ExcelData;

/**
 *	@modified	qin.hua   
 *
 *	@date	2017/6/8
 * 
 **/
public class BaseCase {
	protected RestClient rc = new RestClient();
	private String url;
	
	private static List<String> testUrlList ; 
	
	
	/*
	 * 默认构造函数，指定url为jzUrl
	 */
	public BaseCase() {
		this.url = "baseUrl";
	}

	/*
	 * 赋参构造函数，指定url为子类传递的url
	 */
	public BaseCase(String url) {
		this.url = url;
	}
	
	

	@BeforeClass
	public void beforeClass() {
		rc.setUrl(this.url);// 设置url前缀，在env.properties中配置
		rc.setEnCode();// 设置请求参数编码为utf8
//		rc.initTestData();// 初始化测试数据
	}
	
/*	@AfterSuite
	public void afterSuite(){
		System.out.println("当前已统计了 " + testUrlList.size() + " 个接口");
	}*/
	
	/*
	 * 为家装测试用例提供参数
	 * @param	method:被提供参数的方法名
	 * 
	 * @author qin.hua
	 */
	@DataProvider(name="providerData",  parallel = true)
	public Object[][] providerTestData(Method method) {//Method 对象参数是调用该DataProvider提供方法的那个，客户方法；
		//获取@Api的名称,名称必须与写入参数的excel名称一致
		Api myApi = method.getDeclaringClass().getAnnotation(Api.class);
		Assert.assertNotNull(myApi, method.getName().toString() + "没有实现api接口");
		
		//获得excel名称
		String excelFileName = myApi.name() + ".xlsx";
		Object[][] result = null;
		
//		System.out.println("正在测试: " + myApi.name().toString() + "-" + method.getName().toString());
		/*
		 * 从excel中获取所有的场景
		 * excel的sheet名称与method一致
		 */
		result = getParamsFromExcel(excelFileName, method.getName());
		return result;
	}

	
	
	/*
	 * 从excel中获取参数
	 * @param	fileName:excel文件名称
	 * 			sheetName:指定的sheet名
	 * 
	 * @author qin.hua
	 */
	public Object[][]  getParamsFromExcel(String fileName, String sheetName){		
		//读取excel
		Object[][] result = null;
		ExcelData excelData = new ExcelData(fileName, sheetName);
		List<Map<String, String>> list = null;
		try {
			list = excelData.readExcelWithTitle();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(list, fileName + "中的" + sheetName + "未发现可以使用的数据\n");
		//增加对url的统计
		Assert.assertTrue(list.get(0).containsKey(CONST.URL), sheetName + "中没有url参数配置");
		
		countTests(list.get(0).get("url").toString());	
				
		int length = list.size();
		result = new Object[length][1];
		for(int i = 0; i < list.size(); i++){
			result[i][0] = list.get(i);
		}
		return result;
	}
	
	protected void countTests(String methodUrl){
		synchronized(this){
			if(testUrlList == null){
				testUrlList = new ArrayList<String>();
			}
			
			//判断url是否已经存在
			String fullUrl = this.url + methodUrl;
			if(!testUrlList.contains(fullUrl)){
				testUrlList.add(fullUrl);
//				System.out.println(">>>新增: " + fullUrl);
			}
		}
	}
}
