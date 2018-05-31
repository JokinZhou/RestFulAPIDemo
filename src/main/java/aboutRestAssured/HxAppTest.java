/**
 * 
 */
package aboutRestAssured;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static io.restassured.matcher.RestAssuredMatchers.*;

import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

/**
 * @author lenovo
 *
 */
public class HxAppTest {
	 @BeforeClass
	 public void beforeClass(){
	  //指定 URL 和端口号
	  RestAssured.baseURI = "http://api.jiazhuang.uat1.rs.com";
	  RestAssured.port = 80;
	  }
	  
	 /**
	  * 添加了restfulAssured的断言；但是在testng中 只是一个@test 所以失败任意一个也认为是失败的
	  */
	 @Test
	 public void testDecoration(){
		 //given().headers(headers).cookies(cookies).post(url);
		 given().param("id","149").get("/c/hxapp/decoration/detail").then().body("message", equalTo("获取成功"));//equalTo是CoreMatchers包下的静态方法
		 //then()后面可以多个验证判断连用
		 given().param("id","149").get("/c/hxapp/decoration/detail").then().
		 body("dataMap.companyAppellation", equalTo("好好公司(HHGS)")).
		 body("dataMap.companyId", equalTo(149));
		 
		 Response re = given().param("id","149").get("/c/hxapp/decoration/detail");
/*		 given().
	       formParam("formParamName", "value1").//提交表单数据一般在post请求中默认"application/x-www-form-urlencoded"
	       queryParam("queryParamName", "value2").when().post("/something");
		 */
		 //如果是post请求， 那么直接在调用params(Map<String, ?> parametersMap)方法传递一个Map类型作为参数：：params有很多种参数形式
		 
		 if(re.getStatusCode()==200){//如果返回值的code是200
			 re.print();
			 System.out.println("我是标准分割线----------------------");
			 re.getBody().prettyPrint();//格式化打印JSON数据
		 }
		 //调用expect()。。。.when为请求设置预期值(如预期返回值，预期head，cookies,body等)；其效果等同于请求后面加then验证
		 given().param("id","149").expect().body("message", equalTo("获取成功")).when().get("/c/hxapp/decoration/detail");
		// given().param("name", "value").and().
		 ValidatableResponse vr = re.then();
		 //matchesJsonSchemaInClasspath 静态导入自 io.restassured.module.jsv.JsonSchemaValidator
		 //products-schema.json参数是指 放在classPath路径下的products-schema.json；此处可以放在直接放在src/main/resources目录下
		 get("/products").then().assertThat().body(matchesJsonSchemaInClasspath("products-schema.json"));
		 
		 
/*		 //XML响应体也可以验证为一个XML Schema (XSD)或DTD
		 //matchesXsd和matchesDtd方法在Hamcrest matchers里，你可以从io.restassured.matcher.RestAssuredMatchers导入
		 get("/carRecords").then().assertThat().body(matchesXsd(xsd));
		 get("/videos").then().assertThat().body(matchesDtd(dtd));
*/
		 
		 
	 }

}
