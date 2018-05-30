package testCase;

import org.testng.annotations.Test;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;

import comm.Api;
import comm.BaseCase;
import comm.CONST;
import comm.ParametersSet;
import io.restassured.response.Response;

/**
 * @author fei.zhang
 * @date 2017年5月10日
 * 
 * @modified	qin.hua  2017/6/9
 */

@Api(name = "C-CRM")
public class AddNewBookingOrderTest extends BaseCase {
	@Test(dataProvider = CONST.PROVIDER_DATA, description = "预约提交")
	public void addNewBookingOrder(Map<String, String> providerParams) {
		//分析给定的参数
		ParametersSet paramSet = new ParametersSet();
		paramSet.parseProviderParams(providerParams);
		
		//发送请求
		Response response = rc.httpGet(paramSet.verifyParams.get(CONST.URL).toString(), paramSet.requestParams);

		//验证返回
		paramSet.parseResult(response, paramSet.verifyParams.get(CONST.EXCEPTED_RESULT));
	}
}
