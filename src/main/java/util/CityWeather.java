/**
 * 
 */
package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * @author lenovo
 *
 */
public class CityWeather {
    private String url="";
    
    public String geturl() {
        return url;
    }
/*   *//**
    * 网上查的说是为了去除 接口返回值BOM头
    * @param s
    * @return
    *//*
    public static String formatString(String s) {  
        if (s != null) {  
              s = s.replaceAll("\ufeff", "");  
        }  
        return s;  
    }*/
    
    public String getHttpRespone(String cityCode) throws IOException {
        String line = "";
        String httpResults = "";
        url=("http://www.weather.com.cn/data/cityinfo/" + cityCode + ".html");
        try {
            HttpURLConnection connection = URLConnection.getConnection(url);
            // 建立实际的连接
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                httpResults = httpResults + line.toString();
            }
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpResults;
    }
}