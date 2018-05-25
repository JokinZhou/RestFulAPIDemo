/**
 * 
 */
package util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author lenovo
 *
 */
public class Common {
    public static String getJsonValue(String JsonString, String JsonId) {
         String JsonValue = "";
         //trim()去掉字符串首尾的空格
         if (JsonString == null || JsonString.trim().length() < 1) {
             return null;
         }
         try {
             JSONObject obj1 = new JSONObject(JsonString);//将返回的json数据序列化即转化成Json对象
             JsonValue = obj1.getString(JsonId);
         } catch (JSONException e) {
             e.printStackTrace();
         }
         return JsonValue;
     }
}