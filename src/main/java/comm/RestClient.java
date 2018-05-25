/**
 * 
 */
package comm;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.sf.json.JSONObject;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.script.ScriptException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;

/**
 * 封装常用方法
 * @author lenovo
 *
 */
public class RestClient {

	private static List<String> testUrlList = null;

	// ----------------------------------------------------------------------------

	/**
	 * 打印请求返回体
	 * @param response
	 * @param url
	 * @throws UnsupportedEncodingException 
	 */
	private void reportLog(Response response, String url) {
		ITestResult iResult = Reporter.getCurrentTestResult();
		// boolean flag = response.getContentType().contains("application/json")
		// || response.getContentType().contains("application/xml")
		// || response.getContentType().contains("text/html");

		if (response != null && iResult != null) {
			iResult.setAttribute("baseUri", RestAssured.baseURI);
			String body = response.body().asString();
			if (body.length() > 1000) {
				iResult.setAttribute("realResponse", body.substring(0, 1000));
			} else {
				iResult.setAttribute("realResponse", body);
			}

		}

		// CONST.RESPONSE_BODY = response.body().asString();
		if (response.getContentType().contains("application/json")
				|| response.getContentType().contains("application/xml")) {
			Reporter.log(url + "返回： " + response.body().asString());
		} else {
			Reporter.log(url + "返回结果不是json或者xml");
		}

	}

	/**
	 * 带cookies、参数的get请求
	 * @param url
	 * @param cookies
	 * @param params
	 * @return
	 */
	public Response httpGet(String url, Cookies cookies, Map<String, ?> params) {
		// countTests(url, "get");
		Response response = given().cookies(cookies).params(params).get(url);
		reportLog(response, url);
		// return given().cookies(cookies).params(params).get(url);
		return response;
	}

	/**
	 * 带cookies的get请求
	 * @param url
	 * @param cookies
	 * @return
	 */
	public Response httpGet(String url, Cookies cookies) {
		// countTests(url, "get");
		Response response = given().cookies(cookies).get(url);
		reportLog(response, url);

		// return given().cookies(cookies).get(url);
		return response;
	}

	/**
	 * 带参数的get请求
	 * @param url
	 * @param params
	 * @return
	 */
	public Response httpGet(String url, Map<String, ?> params) {
		// countTests(url, "get");
		Response response = given().params(params).get(url);
		reportLog(response, url);
		// return given().params(params).get(url);
		return response;
	}

	/**
	 * 带header的get请求
	 * @param url
	 * @param headers
	 * @return
	 */
	public Response httpGet(String url, Headers headers) {
		// countTests(url, "get");
		Response response = given().headers(headers).get(url);
		reportLog(response, url);

		// return given().headers(headers).get(url);
		return response;
	}

	/**
	 * 带headers、参数的get请求
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public Response httpGet(String url, Headers headers, Map<String, ?> params) {
		// countTests(url, "get");
		Response response = given().headers(headers).params(params).get(url);
		reportLog(response, url);

		// return given().headers(headers).params(params).get(url);
		return response;
	}

	/**
	 * 带headers、cookies、参数的get请求
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public Response httpGet(String url, Headers headers, Cookies cookies, Map<String, ?> params) {
		// countTests(url, "get");
		Response response = given().headers(headers).cookies(cookies).params(params).get(url);
		reportLog(response, url);
		// return given().headers(headers).params(params).get(url);
		return response;
	}

	/**
	 * 只带url的get请求
	 * @param url
	 * @return
	 */
	public Response httpGet(String url) {
		// countTests(url, "get");
		Response response = get(url);
		reportLog(response, url);
		// return get(url);
		return response;
	}

	/**
	 * 带headers、cookies、表单参数的post请求
	 * @param url
	 * @param headers
	 * @param cookies
	 * @param params
	 * @return
	 */
	public Response httpPost(String url, Headers headers, Cookies cookies, Map<String, ?> params) {
		// countTests(url, "post");
		Response response = given().headers(headers).cookies(cookies).params(params).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().headers(headers).cookies(cookies).params(params).post(url);
	}

	/**
	 * 带header、cookies的post请求
	 * @param url
	 * @param headers
	 * @param cookies
	 * @return
	 */
	public Response httpPost(String url, Headers headers, Cookies cookies) {
		// countTests(url, "post");
		Response response = given().headers(headers).cookies(cookies).post(url);
		reportLog(response, url);
		return response;
		// return given().headers(headers).cookies(cookies).post(url);
	}

	/**
	 * 带headers的post请求
	 * @param url
	 * @param headers
	 * @return
	 */
	public Response httpPost(String url, Headers headers) {
		// countTests(url, "post");
		Response response = given().headers(headers).post(url);
		reportLog(response, url);
		return response;
		// return given().headers(headers).post(url);
	}

	/**
	 * 只带url的post请求
	 * @param url
	 * @return
	 */
	public Response httpPost(String url) {
		// countTests(url, "post");
		Response response = post(url);
		reportLog(response, url);
		return response;
		// return post(url);
	}

	/**
	 * 带headers、表单参数的post请求
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public Response httpPost(String url, Headers headers, Map<String, ?> params) {
		// countTests(url, "post");
		Response response = given().headers(headers).params(params).post(url);
		reportLog(response, url);
		return response;
		// return given().headers(headers).params(params).post(url);
	}

	/**
	 * 带cookies、表单参数的post请求
	 * @param url
	 * @param cookies
	 * @param params
	 * @return
	 */
	public Response httpPost(String url, Cookies cookies, Map<String, ?> params) {
		// countTests(url, "post");
		Response response = given().cookies(cookies).params(params).post(url);
		reportLog(response, url);
		return response;
		// return given().cookies(cookies).params(params).post(url);
	}

	/**
	 * 带cookies的post请求
	 * @param url
	 * @param cookies
	 * @return
	 */
	public Response httpPost(String url, Cookies cookies) {
		// countTests(url, "post");
		Response response = given().cookies(cookies).post(url);
		reportLog(response, url);
		return response;
		// return given().cookies(cookies).post(url);
	}

	/**
	 * 带表单参数的post请求
	 * @param url
	 * @param params
	 * @return
	 */
	public Response httpPost(String url, Map<String, ?> params) {
		// countTests(url, "post");
		Response response = given().params(params).post(url);
		reportLog(response, url);
		return response;
		// return given().params(params).post(url);
	}

	/**
	 * 带headers、cookies、查询参数的post请求
	 * @param url
	 * @param headers
	 * @param cookies
	 * @param queryParams
	 * @return
	 */
	public Response httpPostQuery(String url, Headers headers, Cookies cookies, Map<String, ?> queryParams) {
		// countTests(url, "post");
		Response response = given().headers(headers).cookies(cookies).queryParams(queryParams).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().headers(headers).cookies(cookies).queryParams(queryParams).post(url);
	}

	/**
	 * 带headers、查询参数的post请求
	 * @param url
	 * @param headers
	 * @param queryParams
	 * @return
	 */
	public Response httpPostQuery(String url, Headers headers, Map<String, ?> queryParams) {
		// countTests(url, "post");
		Response response = given().headers(headers).queryParams(queryParams).post(url);
		reportLog(response, url);
		return response;
		// return given().headers(headers).queryParams(queryParams).post(url);
	}

	/**
	 * 带cookies、查询参数的post请求
	 * @param url
	 * @param cookies
	 * @param queryParams
	 * @return
	 */
	public Response httpPostQuery(String url, Cookies cookies, Map<String, ?> queryParams) {
		// countTests(url, "post");
		Response response = given().cookies(cookies).queryParams(queryParams).post(url);
		reportLog(response, url);
		return response;
		// return given().cookies(cookies).queryParams(queryParams).post(url);
	}

	/**
	 * 带查询参数的post请求
	 * @param url
	 * @param queryParams
	 * @return
	 */
	public Response httpPostQuery(String url, Map<String, ?> queryParams) {
		// countTests(url, "post");
		Response response = given().queryParams(queryParams).post(url);
		reportLog(response, url);
		return response;
		// return given().queryParams(queryParams).post(url);
	}

	/**
	 * 带cookies、参数为json的post请求
	 * @param url
	 * @param cookies
	 * @param body
	 * @return
	 */
	public Response httpPostBody(String url, Cookies cookies, Object body) {
		// countTests(url, "post");
		Response response = given().contentType("application/json").cookies(cookies).body(body).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().contentType("application/json").cookies(cookies).body(body).post(url);
	}

	/**
	 * 带headers、参数为json的post请求
	 * @param url
	 * @param headers
	 * @param body
	 * @return
	 */
	public Response httpPostBody(String url, Headers headers, Object body) {
		// countTests(url, "post");
		Response response = given().contentType("application/json").headers(headers).body(body).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().contentType("application/json").headers(headers).body(body).post(url);
	}

	/**
	 * 带headers、cookies、参数为json的post请求
	 * @param url
	 * @param headers
	 * @param body
	 * @return
	 */
	public Response httpPostBody(String url, Headers headers, Cookies cookies, Object body) {
		// countTests(url, "post");
		Response response = given().contentType("application/json").headers(headers).cookies(cookies).body(body)
				.post(url);
		reportLog(response, url);
		return response;
		// return
		// given().contentType("application/json").headers(headers).body(body).post(url);
	}

	/**
	 * 参数类型为json的post请求
	 * @param url
	 * @param body
	 * @return
	 */
	public Response httpPostBody(String url, Object body) {
		// countTests(url, "post");
		Response response = given().contentType("application/json").body(body).post(url);
		reportLog(response, url);
		return response;
		// return given().contentType("application/json").body(body).post(url);
	}

	/**
	 * 带headers、cookies、参数类型为文件的post请求
	 * @param url
	 * @param headers
	 * @param cookies
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file
	 * @return
	 */
	public Response httpPostFile(String url, Headers headers, Cookies cookies, String controlName, File file) {
		// countTests(url, "post");
		Response response = given().headers(headers).cookies(cookies).multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().headers(headers).cookies(cookies).multiPart(controlName,
		// file).post(url);
		// for (Map.Entry<String, File> item : files.entrySet()) {
		// given().multiPart(item.getKey(), item.getValue());
		// }
		// return given().post(url);
	}

	/**
	 * 带headers、参数类型为文件的post请求
	 * @param url
	 * @param headers
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file
	 * @return
	 */
	public Response httpPostFile(String url, Headers headers, String controlName, File file) {
		// countTests(url, "post");
		Response response = given().headers(headers).multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return given().headers(headers).multiPart(controlName,
		// file).post(url);
	}

	/**
	 * 带cookies、参数类型为文件的post请求
	 * @param url
	 * @param cookies
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file
	 * @return
	 */
	public Response httpPostFile(String url, Cookies cookies, String controlName, File file) {
		// countTests(url, "post");
		Response response = given().cookies(cookies).multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return given().cookies(cookies).multiPart(controlName,
		// file).post(url);
	}

	/**
	 * 参数类型为文件的post请求
	 * @param url
	 * @param url
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file
	 * @return
	 */
	public Response httpPostFile(String url, String controlName, File file) {
		// countTests(url, "post");
		Response response = given().multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return given().multiPart(controlName, file).post(url);
	}

	/**
	 * 带headers、cookies、查询参数、表单参数、文件的post请求
	 * @param url
	 * @param headers
	 * @param cookies
	 * @param queryParams
	 * @param formParams
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file
	 * @return
	 */
	public Response httpPostHCQueryFormFile(String url, Headers headers, Cookies cookies, Map<String, ?> queryParams,
			Map<String, ?> formParams, String controlName, File file) {
		// countTests(url, "post");
		Response response = given().headers(headers).cookies(cookies).queryParams(queryParams).formParams(formParams)
				.multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().headers(headers).cookies(cookies).queryParams(queryParams).formParams(formParams)
		// .multiPart(controlName, file).post(url);
	}

	/**
	 * 带headers、cookies、查询参数、表单参数的post请求
	 * @param url
	 * @param headers
	 * @param cookies
	 * @param queryParams
	 * @param formParams
	 * @return
	 */
	public Response httpPostHCQueryForm(String url, Headers headers, Cookies cookies, Map<String, ?> queryParams,
			Map<String, ?> formParams) {
		// countTests(url, "post");
		Response response = given().headers(headers).cookies(cookies).queryParams(queryParams).formParams(formParams)
				.post(url);
		reportLog(response, url);
		return response;
		// return
		// given().headers(headers).cookies(cookies).queryParams(queryParams).formParams(formParams).post(url);
	}

	/**
	 * 带headers、cookies、查询参数、文件的post请求
	 * @param url
	 * @param headers
	 * @param cookies
	 * @param queryParams
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file
	 * @return
	 */
	public Response httpPostHCQueryFile(String url, Headers headers, Cookies cookies, Map<String, ?> queryParams,
			String controlName, File file) {
		// countTests(url, "post");
		Response response = given().headers(headers).cookies(cookies).queryParams(queryParams)
				.multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().headers(headers).cookies(cookies).queryParams(queryParams).multiPart(controlName,
		// file)
		// .post(url);
	}

	/**
	 * 带headers、cookies、表单参数、文件的post请求
	 * @param url
	 * @param headers
	 * @param cookies
	 * @param formParams
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file
	 * @return
	 */
	public Response httpPostHCFormFile(String url, Headers headers, Cookies cookies, Map<String, ?> formParams,
			String controlName, File file) {
		// countTests(url, "post");
		Response response = given().headers(headers).cookies(cookies).formParams(formParams)
				.multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().headers(headers).cookies(cookies).formParams(formParams).multiPart(controlName,
		// file).post(url);
	}

	/**
	 * 带headers、查询参数、表单参数、文件的post请求
	 * @param url
	 * @param headers
	 * @param queryParams
	 * @param formParams
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file
	 * @return
	 */
	public Response httpPostHQueryFormFile(String url, Headers headers, Map<String, ?> queryParams,
			Map<String, ?> formParams, String controlName, File file) {
		// countTests(url, "post");
		Response response = given().headers(headers).queryParams(queryParams).formParams(formParams)
				.multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().headers(headers).queryParams(queryParams).formParams(formParams).multiPart(controlName,
		// file)
		// .post(url);
	}

	/**
	 * 带headers、查询参数、表单参数的post请求
	 * @param url
	 * @param headers
	 * @param queryParams
	 * @param formParams
	 * @return
	 */
	public Response httpPostHQueryForm(String url, Headers headers, Map<String, ?> queryParams,
			Map<String, ?> formParams) {
		// countTests(url, "post");
		Response response = given().headers(headers).queryParams(queryParams).formParams(formParams).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().headers(headers).queryParams(queryParams).formParams(formParams).post(url);
	}

	/**
	 * 带headers、查询参数、文件的post请求
	 * @param url
	 * @param headers
	 * @param queryParams
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file
	 * @return
	 */
	public Response httpPostHQueryFile(String url, Headers headers, Map<String, ?> queryParams, String controlName,
			File file) {
		// countTests(url, "post");
		Response response = given().headers(headers).queryParams(queryParams).multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().headers(headers).queryParams(queryParams).multiPart(controlName,
		// file).post(url);
	}

	/**
	 * 带headers、表单参数、文件的post请求
	 * @param url
	 * @param headers
	 * @param formParams
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file
	 * @return
	 */
	public Response httpPostHFormFile(String url, Headers headers, Map<String, ?> formParams, String controlName,
			File file) {
		// countTests(url, "post");
		Response response = given().headers(headers).formParams(formParams).multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().headers(headers).formParams(formParams).multiPart(controlName,
		// file).post(url);
	}

	/**
	 * 带cookies、查询参数、表单参数、文件的post请求
	 * @param url
	 * @param cookies
	 * @param queryParams
	 * @param formParams
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file
	 * @return
	 */
	public Response httpPostCQueryFormFile(String url, Cookies cookies, Map<String, ?> queryParams,
			Map<String, ?> formParams, String controlName, File file) {
		// countTests(url, "post");
		Response response = given().cookies(cookies).queryParams(queryParams).formParams(formParams)
				.multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().cookies(cookies).queryParams(queryParams).formParams(formParams).multiPart(controlName,
		// file)
		// .post(url);
	}

	/**
	 * 带cookies、查询参数、表单参数的post请求
	 * @param url
	 * @param cookies
	 * @param queryParams
	 * @param formParams
	 * @return
	 */
	public Response httpPostCQueryForm(String url, Cookies cookies, Map<String, ?> queryParams,
			Map<String, ?> formParams) {
		// countTests(url, "post");
		Response response = given().cookies(cookies).queryParams(queryParams).formParams(formParams).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().cookies(cookies).queryParams(queryParams).formParams(formParams).post(url);
	}

	/**
	 * 带cookies、查询参数、文件的post请求
	 * @param url
	 * @param cookies
	 * @param queryParams
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file 
	 * @return
	 */
	public Response httpPostCQueryFile(String url, Cookies cookies, Map<String, ?> queryParams, String controlName,
			File file) {
		// countTests(url, "post");
		Response response = given().cookies(cookies).queryParams(queryParams).multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().cookies(cookies).queryParams(queryParams).multiPart(controlName,
		// file).post(url);
	}

	/**
	 * 带cookies、表单参数、文件的post请求
	 * @param url
	 * @param cookies
	 * @param formParams
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file
	 * @return
	 */
	public Response httpPostCFormFile(String url, Cookies cookies, Map<String, ?> formParams, String controlName,
			File file) {
		// countTests(url, "post");
		Response response = given().cookies(cookies).formParams(formParams).multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().cookies(cookies).formParams(formParams).multiPart(controlName,
		// file).post(url);
	}

	/**
	 * 带查询参数、表单参数、文件的post请求
	 * @param url
	 * @param queryParams
	 * @param formParams
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file 
	 * @return
	 */
	public Response httpPostQueryFormFile(String url, Map<String, ?> queryParams, Map<String, ?> formParams,
			String controlName, File file) {
		// countTests(url, "post");
		Response response = given().queryParams(queryParams).formParams(formParams).multiPart(controlName, file)
				.post(url);
		reportLog(response, url);
		return response;
		// return
		// given().queryParams(queryParams).formParams(formParams).multiPart(controlName,
		// file).post(url);
	}

	/**
	 * 带查询参数、表单参数的post请求
	 * @param url
	 * @param queryParams
	 * @param formParams
	 * @return
	 */
	public Response httpPostQueryForm(String url, Map<String, ?> queryParams, Map<String, ?> formParams) {
		// countTests(url, "post");
		Response response = given().queryParams(queryParams).formParams(formParams).post(url);
		reportLog(response, url);
		return response;
		// return
		// given().queryParams(queryParams).formParams(formParams).post(url);
	}

	/**
	 * 带查询参数、文件的post请求
	 * @param url
	 * @param queryParams
	 * @param controlName
	 * @param file 参数名称（一般情况下是file）
	 * @return
	 */
	public Response httpPostQueryFile(String url, Map<String, ?> queryParams, String controlName, File file) {
		// countTests(url, "post");
		Response response = given().queryParams(queryParams).multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return given().queryParams(queryParams).multiPart(controlName,
		// file).post(url);
	}

	/**
	 * 带表单参数、文件的post请求
	 * @param url
	 * @param formParams
	 * @param controlName 参数名称（一般情况下是file）
	 * @param file
	 * @return
	 */
	public Response httpPostFormFile(String url, Map<String, ?> formParams, String controlName, File file) {
		// countTests(url, "post");
		Response response = given().formParams(formParams).multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
		// return given().formParams(formParams).multiPart(controlName,
		// file).post(url);
	}

	/**
	 * 带headers、json、文件的post请求
	 * @param url
	 * @param headers
	 * @param body
	 * @param controlName
	 * @param file
	 * @return
	 */

	public Response httpPostBodyFile(String url, Headers headers, Object body, String controlName, File file) {
		// countTests(url, "post");
		Response response = given().headers(headers).body(body).multiPart(controlName, file).post(url);
		reportLog(response, url);
		return response;
	}

	/**
	 * 带cookies、参数为json的put请求
	 * @param url
	 * @param cookies
	 * @param body
	 * @return
	 */
	public Response httpPutBody(String url, Cookies cookies, Object body) {
		// countTests(url, "put");
		Response response = given().contentType("application/json").cookies(cookies).body(body).put(url);
		reportLog(response, url);
		return response;
		// return
		// given().contentType("application/json").cookies(cookies).body(body).put(url);
	}

	/**
	 * 带headers、参数为json的put请求
	 * @param url
	 * @param headers
	 * @param body
	 * @return
	 */
	public Response httpPutBody(String url, Headers headers, Object body) {
		// countTests(url, "put");
		Response response = given().contentType("application/json").headers(headers).body(body).put(url);
		reportLog(response, url);
		return response;
		// return
		// given().contentType("application/json").headers(headers).body(body).put(url);
	}

	/**
	 * 参数类型为json的put请求
	 * @param url
	 * @param body
	 * @return
	 */
	public Response httpPutBody(String url, Object body) {
		// countTests(url, "put");
		Response response = given().contentType("application/json").body(body).put(url);
		reportLog(response, url);
		return response;
		// return given().contentType("application/json").body(body).put(url);
	}

	/**
	 * 只带url的delete请求
	 * @param url
	 * @param cookies
	 * @return
	 */
	public Response httpDel(String url) {
		// countTests(url, "del");
		Response response = given().delete(url);
		reportLog(response, url);
		return response;
		// return given().delete(url);
	}

	/**
	 * 带cookies的del请求
	 * @param url
	 * @param cookies
	 * @return
	 */
	public Response httpDel(String url, Cookies cookies) {
		// countTests(url, "del");
		Response response = given().cookies(cookies).delete(url);
		reportLog(response, url);
		return response;
		// return given().cookies(cookies).delete(url);
	}

	/**
	 * 带headers的del请求
	 * @param url
	 * @param cookies
	 * @return
	 */
	public Response httpDel(String url, Headers headers) {
		// countTests(url, "del");
		Response response = given().headers(headers).delete(url);
		reportLog(response, url);
		return response;
		// return given().headers(headers).delete(url);
	}

	/**
	 * 获取响应体验证对象
	 * @param response
	 * @return
	 */
	public ValidatableResponse valResponse(Response response) {
		return response.then();
	}

	/**
	 * 验证状态码等于expectedStatusCode
	 * @param response
	 * @param expectedStatusCode
	 * @return
	 */
	public ValidatableResponse statusCode(Response response, int expectedStatusCode) {
		return response.then().statusCode(expectedStatusCode);
	}

	/**
	 * 验证返回体字段值与预期是否相等
	 * @param response
	 * @param path
	 * @param value
	 * @return
	 */
	public ValidatableResponse equal(Response response, String path, Object value) {
		return response.then().body(path, equalTo(value));
	}

/*	*//**
	 * 验证jsonSchema是否正确
	 * @param response
	 * @param jsonPath
	 * @return
	 *//*
	public ValidatableResponse matchesJsonSchema(Response response, String jsonPath) {
		return response.then().body(matchesJsonSchemaInClasspath(jsonPath));
	}*/

/*	*//**
	 * 返回体包含json数组，判断json数组中字段key包含值value
	 * @param response
	 * @param path  
	 * @param key
	 * @param value
	 *//*
	public void assertContains(Response response, String path, String key, String value) {
		Utils.assertContains(response, path, key, value);
	}*/

	/**
	 * 断言请求响应时间小于ms毫秒
	 * @param response
	 * @param ms
	 * @return
	 */
	public ValidatableResponse timeLessThan(Response response, long ms) {
		return response.then().time(lessThan(ms));
	}

	/**
	 * 生成随机手机号
	 * @return
	 */
	public String getRandomTel() {
		return RandomValue.getTel();
	}

/*	*//**
	 * 将ResultSet转化为JSON数组
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 *//*
	public JSONArray resultSetToJsonArry(String sql) throws SQLException, JSONException {
		return Utils.resultSetToJsonArry(sql);
	}

	public JSONArray resultSetToJsonArry(String sql, String dbUrl, String dbUserName, String dbPwd)
			throws SQLException, JSONException {
		return Utils.resultSetToJsonArry2(sql, dbUrl, dbUserName, dbPwd);
	}
*/
/*	*//**
	 * 提取json数组字段key的值到list中
	 * @param jsonArray
	 * @param key
	 * @return
	 *//*
	public List<String> getArrayFromJsonArray(JSONArray jsonArray, String key) {
		return Utils.getArrayFromJsonArray(jsonArray, key);
	}
*/
	public static void main(String[] args) {
		RestClient rc = new RestClient();

		System.out.println(rc.getRandomTel());

	}

}

