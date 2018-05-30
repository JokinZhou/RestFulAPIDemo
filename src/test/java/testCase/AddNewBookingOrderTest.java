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
//这个@Api注释给BaseCase类中的DataProvider测试对于excel的文件名
public class AddNewBookingOrderTest extends BaseCase {
	@Test(dataProvider = CONST.PROVIDER_DATA, description = "预约提交")
	public void addNewBookingOrder(Map<String, String> providerParams) {//调用了dataProvider参数注释的方法如：addNewBookingOrder， 可通过Method对象传送给DataProvider提供方法
		//分析给定的参数 ， 参数是通过dataProvider提供的数据，改方法读取的具体实现在BaseCase类中
		ParametersSet paramSet = new ParametersSet();
		paramSet.parseProviderParams(providerParams);
		
		//发送请求
		Response response = rc.httpGet(paramSet.verifyParams.get(CONST.URL).toString(), paramSet.requestParams);

		//验证返回
		paramSet.parseResult(response, paramSet.verifyParams.get(CONST.EXCEPTED_RESULT));
	}
}
