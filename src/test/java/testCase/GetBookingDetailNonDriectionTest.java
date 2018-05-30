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
		//分析给定的参数
		ParametersSet paramSet = new ParametersSet();
		paramSet.parseProviderParams(providerParams);
		
		Reporter.log("url:" + providerParams.get(CONST.URL).toString());
		// 发送post请求
		Response response = rc.httpGet(providerParams.get(CONST.URL).toString(), headers, paramSet.requestParams);

		//验证结果
		paramSet.parseResult(response, paramSet.verifyParams.get(CONST.EXCEPTED_RESULT));
	}
}
