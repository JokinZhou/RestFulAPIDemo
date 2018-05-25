/**
 * 
 */
package aboutRestAssured;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.*;
//import static io.restassured.module.jsv.JsonSchemaValidator.*;


/**
 * @author lenovo
 *
 */
public class DouBanTest
{
 @BeforeClass
public void beforeClass(){
 //指定 URL 和端口号
 RestAssured.baseURI = "https://api.douban.com/v2/book";
 RestAssured.port = 80;
 }
 
 @Test
 public void testGETBook(){
	 //given().headers(headers).cookies(cookies).post(url);
	 given().get("/1220562").then().body("title", equalTo("满月之夜白鲸现"));//equalTo是CoreMatchers包下的静态方法
 }
 
}
