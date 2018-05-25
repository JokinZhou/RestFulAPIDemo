package util;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class TestCase {
    public String httpResult= null, city=null,expect_city = null;
    String tianqi= null;
    public static String cityCode="";
    CityWeather weather=new CityWeather();
    String JSonId = "weatherinfo";
    
    @Test(priority=0)
    public void getHuaihua() throws IOException{
        expect_city="怀化";
        cityCode="101251201";
        resultCheck(cityCode, expect_city);
    }
    
    @Test(priority=1)
    public void getHuitong() throws IOException{
        expect_city="会同";
        cityCode="101251206";
        resultCheck(cityCode, expect_city);
    }
    
    public void resultCheck(String cityCode, String expect_city) throws IOException{
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        Reporter.log("【正常用例】:获取"+expect_city+"天气成功!");
        httpResult=weather.getHttpRespone(cityCode);
        Reporter.log("<p><span style=\"color:#FF0000\">请求地址: "+weather.geturl()+"</span></p>");
        Reporter.log("【返回结果】: "+httpResult);
        System.out.println("【返回结果】: "+httpResult);
        
        tianqi=Common.getJsonValue(httpResult, JSonId);
        System.out.println("将返回的字符流变成字符串后，然后在转化成json，更具key值得到最终想要的数据【返回结果】: "+ tianqi);
        //city=Common.getJsonValue(weatherinfo, "city");
        Reporter.log("<p>【用例结果】: resultCode=>expected: " + expect_city + " ,actual: "+ city+"</p>");
        Assert.assertEquals(city,expect_city);  
        Reporter.log("<p></p>");
        Reporter.log("<p>"+"------------------------------------------------------------------------------"+"</p>");
    }
}
