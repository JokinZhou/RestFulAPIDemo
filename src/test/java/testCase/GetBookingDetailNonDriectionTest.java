package testCase;

import java.util.HashMap;
import java.util.Map;

import org.testng.Reporter;
import org.testng.annotations.Test;

import comm.BaseCase;
import comm.CONST;
import comm.Login;
import comm.ParametersSet;
import io.restassured.http.Headers;
import io.restassured.response.Response;


/**
 *	@author	qin.hua   
 *
 *	@date	2017/6/8
 * 
 **/
@comm.Api(name = "C-预约")
public class GetBookingDetailNonDriectionTest extends BaseCase {
	private Headers headers = null;
	
	public GetBookingDetailNonDriectionTest() {
		super("jzDirectionUrl");
		
		//登录
		headers = Login.getCHeaders();
	}

//	private String url = "/c/order/noBookingDetail";

	@Test(description = "预约详情接口-非定向", dataProvider = CONST.PROVIDER_DATA)
	public void getBookingDetailNonDirection(Map<String, String> providerParams) {
		//分析给定的参数1、初始化一个ParametersSet对象，里面有两个成员变量requestParams请求参数，和verifyParams预期结果
		ParametersSet paramSet = new ParametersSet();
		//分析给定的参数2、解析dataProvider提供的数据，给两个成员变量赋值
		paramSet.parseProviderParams(providerParams);
		
/*		Reporter.log("url:" + providerParams.get(CONST.URL).toString());
		System.out.println(providerParams.get(CONST.URL).toString()+"这是标准的分割线-----------------");*/
		// 发送请求
		Response response = rc.httpGet(providerParams.get(CONST.URL).toString(), headers, paramSet.requestParams);

		//验证结果
		paramSet.parseResult(response, paramSet.verifyParams.get(CONST.EXCEPTED_RESULT));
	}
}
