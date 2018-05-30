package comm;

import static org.testng.AssertJUnit.assertTrue;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import io.restassured.response.Response;
import util.Dao;

public class Utils {
	private static final String CHARSET_UTF8 = "UTF-8";

	/**
	 * 将ResultSet转化为JSON数组
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @throws JSONException
	 */
	public static JSONArray resultSetToJsonArry(String sql) throws SQLException, JSONException {
		Dao dao = new Dao();
		Connection conn = dao.connSql();
		PreparedStatement statement;

		statement = conn.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		// json数组
		JSONArray array = new JSONArray();
		// 获取列数
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		// 遍历ResultSet中的每条数据
		while (rs.next()) {
			JSONObject jsonObj = new JSONObject();

			// 遍历每一列
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnLabel(i);
				String value = rs.getString(columnName);
				jsonObj.put(columnName, value);
			}
			array.add(jsonObj);
		}
		statement.close();
		conn.close();
		return array;
	}

	public static JSONArray resultSetToJsonArry2(String sql, String dbUrl, String dbUserName, String dbPwd)
			throws SQLException, JSONException {
		Dao dao = new Dao();
		Connection conn = dao.connSql2(dbUrl, dbUserName, dbPwd);
		PreparedStatement statement;

		statement = conn.prepareStatement(sql);
		ResultSet rs = statement.executeQuery();

		// json数组
		JSONArray array = new JSONArray();
		// 获取列数
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		// 遍历ResultSet中的每条数据
		while (rs.next()) {
			JSONObject jsonObj = new JSONObject();

			// 遍历每一列
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnLabel(i);
				String value = rs.getString(columnName);
				jsonObj.put(columnName, value);
			}
			array.add(jsonObj);
		}
		statement.close();
		conn.close();
		return array;
	}

	/**
	 * 提取json数组字段key的值到list中
	 * @param jsonArray
	 * @param key
	 * @return
	 */
	public static List<String> getArrayFromJsonArray(JSONArray jsonArray, String key) {
		List<String> keyList = new ArrayList<>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String value = jsonObject.getString(key);
			keyList.add(value);
		}
		return keyList;

	}

	/**
	 * 返回体包含json数组，判断json数组中字段key包含值value
	 * @param response
	 * @param path  
	 * @param key
	 * @param value
	 */
	public static void assertContains(Response response, String path, String key, String value) {
		List<String> codeList = new ArrayList<>();
		JSONArray jsonArray = new JSONArray((List<Object>) response.path(path));
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String code = jsonObject.getString(key);
			codeList.add(code);
		}
		boolean flag = codeList.contains(value);
		assertTrue(flag);
	}

	/**
	 * 从env中读取数据
	 * @param name
	 * @return  string
	 */
	public static String getValueFromEnv(String name) {
		return Config.getProp(name);
	}

	/**
	 * 从env中读取数据
	 * @param name
	 * @return  int
	 */
	public static int getValueFromEnvToInt(String name) {
		return Integer.valueOf(Config.getProp(name));
	}

	/**
	 * 转义正则特殊字符 （$()*+.[]?\^{},|）
	 * 
	 * @param keyword
	 * @return
	 */
	public static String escapeExprSpecialWord(String keyword) {
		if (isNotEmpty(keyword)) {
			String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
			for (String key : fbsArr) {
				if (keyword.contains(key)) {
					keyword = keyword.replace(key, "\\" + key);
				}
			}
		}
		return keyword;
	}

	public static String[] fromList(List<String> list) {
		if (Utils.isNotEmpty(list)) {
			return list.toArray(new String[] {});
		}
		return null;
	}

	public static List<String> toList(String[] strArr) {
		if (Utils.isNotEmpty(strArr)) {
			return Arrays.asList(strArr);
		}
		return null;
	}

	public static String resetNull(String nullStr) {
		if (Utils.isEmpty(nullStr)) {
			nullStr = "";
		}
		return nullStr;
	}

	public static String connectStrs(String... strings) {
		StringBuilder sb = new StringBuilder(255);
		for (String string : strings) {
			sb.append(string);
		}
		return sb.toString().trim();
	}

	public static String strRandom(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static int intRandom(int length) { // length表示生成字符串的长度
		Random random = new Random();// 使用种子缺省是当前系统时间的毫秒数的Random对象生成[0,10)内随机整数序列
		return random.nextInt(length);
	}

	public static double doubleRandom(int length) { // length表示生成字符串的长度
		Random random = new Random();// 使用种子缺省是当前系统时间的毫秒数的Random对象生成[0,10)内随机整数序列
		double randomDouble = random.nextDouble();
		BigDecimal b = new BigDecimal(randomDouble);
		return b.setScale(length, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static List<Integer> toIntList(List<String> list) {
		List<Integer> results = new ArrayList<Integer>();
		for (String item : list) {
			results.add(Utils.toInt(item));
		}
		return results;
	}

	public static List<Integer> toIntList(List<String> list, int filter) {
		List<Integer> results = new ArrayList<Integer>();
		for (String item : list) {
			int intItem = Utils.toInt(item);
			if (intItem > filter) {
				results.add(intItem);
			}
		}
		return results;
	}

	public static <K, V> Map<K, V> mapDiff(Map<K, V> map1, Map<K, V> map2, int flag) {
		Map<Map.Entry<K, V>, Integer> map = new HashMap<Map.Entry<K, V>, Integer>();
		for (Entry<K, V> itemMap1 : map1.entrySet()) {
			map.put(itemMap1, 1);
		}
		for (Map.Entry<K, V> itemMap2 : map2.entrySet()) {
			if (map.get(itemMap2) != null) {
				map.put(itemMap2, 2);
			} else {
				map.put(itemMap2, 3);
			}
		}
		Map<K, V> result = new HashMap<K, V>();
		for (Entry<Map.Entry<K, V>, Integer> mapItem : map.entrySet()) {
			if (mapItem.getValue() == 1) {
				Entry<K, V> resultItem = mapItem.getKey();
				result.put(resultItem.getKey(), resultItem.getValue());
			}
		}
		return result;
	}

	public static List<String> listDiff(List<String> list1, List<String> list2, int difflag) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 把list1放在map value为1
		if (Utils.isNotEmpty(list1)) {
			for (String item : list1) {
				map.put(item, 1);
			}
		}
		// 把list2放在map value为3，如果在map有就为2
		if (Utils.isNotEmpty(list2)) {
			for (String item : list2) {
				if (map.get(item) != null) {
					map.put(item, 2);
				} else {
					map.put(item, 3);
				}
			}
		}
		List<String> result = new ArrayList<String>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == difflag) {
				result.add(entry.getKey());
			}
		}
		return result;
	}

	public static List<List<String>> listDiff(List<String> list1, List<String> list2) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 把list1放在map value为1
		if (Utils.isNotEmpty(list1)) {
			for (String item : list1) {
				map.put(item, 1);
			}
		}
		// 把list2放在map value为3，如果在map有就为2
		if (Utils.isNotEmpty(list2)) {
			for (String item : list2) {
				if (map.get(item) != null) {
					map.put(item, 2);
				} else {
					map.put(item, 3);
				}
			}
		}
		List<String> commLists = new ArrayList<String>();
		List<String> commLists1 = new ArrayList<String>();
		List<String> commLists2 = new ArrayList<String>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 2) {
				commLists.add(entry.getKey());
			} else if (entry.getValue() == 1) {
				commLists1.add(entry.getKey());
			} else if (entry.getValue() == 3) {
				commLists2.add(entry.getKey());
			}
		}
		List<List<String>> all = new ArrayList<List<String>>();
		all.add(commLists);
		all.add(commLists1);
		all.add(commLists2);
		return all;
	}

	/*****
	 * 利用正则表达拿到值matcher.group(0) matcher.group(0)指的整个串
	 * 
	 * @param value
	 * @param regex
	 * @return StringList
	 */
	public static List<String> getMatcherListG0(String value, String regex) {
		Matcher matcher = null;
		try {
			Pattern pattern = Pattern.compile(regex);
			matcher = pattern.matcher(value);
			List<String> result = new ArrayList<String>();
			while (matcher.find()) {
				if (matcher.group(0) != null) {
					result.add(matcher.group(0));
				}
			}
			return result;
		} catch (Exception ex) {
			return null;
		}
	}

	/*****
	 * 利用正则表达拿到值matcher.group(0) matcher.group(0)指的整个串
	 * 
	 * @param value
	 * @param regex
	 * @return StringList
	 */
	public static String getMatcherG0(String value, String regex) {
		List<String> result = getMatcherListG0(value, regex);
		if (isNotEmpty(result)) {
			return result.get(0);
		} else {
			return "";
		}
	}

	/*****
	 * 利用正则表达拿到值matcher.group(1) group（1） 指的是第一个括号里的东西
	 * 
	 * @param value
	 * @param regex
	 * @return StringList
	 */
	public static List<String> getMatcherListG1(String value, String regex) {
		Matcher matcher = null;
		try {
			Pattern pattern = Pattern.compile(regex);
			matcher = pattern.matcher(value);
			List<String> result = new ArrayList<String>();
			while (matcher.find()) {
				if (matcher.group(1) != null) {
					result.add(matcher.group(1));
				}
			}
			return result;
		} catch (Exception ex) {
			return null;
		}
	}

	/*****
	 * 利用正则表达拿到值matcher.group(1) group（1） 指的是第一个括号里的东西 Sample :
	 * {"code":-403,"message":"Need login","data":null}
	 * Utils.getMatcherG1(result.getResult(), "\"code\":(.*?),");
	 * 
	 * @param value
	 * @param regex
	 * @return StringList
	 */
	public static String getMatcherG1(String value, String regex) {
		List<String> result = getMatcherListG1(value, regex);
		if (isNotEmpty(result)) {
			return result.get(0);
		} else {
			return "";
		}
	}

	public static String UrlEncode(String param) {
		try {
			return URLEncoder.encode(param, CHARSET_UTF8);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * http://www.cnblogs.com/jiunadianshi/articles/2353968.html + URL 中+号表示空格
	 * %2B 空格 URL中的空格可以用+号或者编码 %20 / 分隔目录和子目录 %2F ? 分隔实际的URL和参数 %3F % 指定特殊字符 %25
	 * # 表示书签 %23 & URL 中指定的参数间的分隔符 %26 = URL 中指定参数的值 %3D
	 ** 
	 * @Title UrlSpaceReplace
	 ** @Description
	 ** @Return String 返回类型
	 ** @Throws
	 */
	public static String UrlSpecialCharReplace(String url) {
		return url.replace(" ", "%20");
	}

	public static String UrlDecode(String param) {
		try {
			return URLDecoder.decode(param, CHARSET_UTF8);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static UrlEncodedFormEntity toEntity(List<NameValuePair> params) {
		try {
			return new UrlEncodedFormEntity(params, CHARSET_UTF8);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static boolean isEmpty(Set<?> set) {
		if (set != null && set.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNotEmpty(Set<?> set) {
		if (set != null && set.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(String[] strings) {
		if (strings != null && strings.length > 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNotEmpty(String[] strings) {
		if (strings != null && strings.length > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(Object object) {
		if (object != null) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNotEmpty(Object object) {
		if (object != null) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(List<?> list) {
		if (list != null && list.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNotEmpty(List<?> list) {
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(String string) {
		if (string != null && string.isEmpty() == false) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNotEmpty(String string) {
		if (string != null && string.isEmpty() == false) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(Map<?, ?> map) {
		if (map != null && map.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNotEmpty(Map<?, ?> map) {
		if (map != null && map.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void sleep(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (Exception ex) {
		}
	}

	public static void waitMillis(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception ex) {
		}
	}

	public static int toInt(String string) {
		try {
			return Integer.parseInt(string);
		} catch (Exception e) {
			return 0;
		}
	}

	public static int toInt(String string, int defaultNum) {
		try {
			return Integer.valueOf(string);
		} catch (Exception e) {
			return defaultNum;
		}
	}

	public static float toFloat(String string) {
		return Float.parseFloat(string);
	}

	public static Double toDouble(String string) {
		return Double.parseDouble(string);
	}

	public static String getPercent(double percent) {
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
		format.setMinimumFractionDigits(0);// 设置小数位
		return format.format(percent / 100.0);
	}

	public static String toStr(int i) {
		return String.valueOf(i);
	}

	public static String toStr(Object obj) {
		return String.valueOf(obj);
	}

	public static long toLong(String string) {
		return Long.parseLong(string);
	}

	public static long toLong(String string, long defaultNum) {
		try {
			return Long.valueOf(string);
		} catch (Exception e) {
			return defaultNum;
		}
	}

	public static String getOSInfo() {
		LocalMachineInfo lmi = new LocalMachineInfo();
		return lmi.getOSInfo();
	}

	public static String getOSName() {
		LocalMachineInfo lmi = new LocalMachineInfo();
		return lmi.getOSName();
	}

	public static String getHostname() {
		LocalMachineInfo lmi = new LocalMachineInfo();
		return lmi.getHostname();
	}

	public static String getCurrentUser() {
		return System.getProperty("user.name");
	}

	public static String getCurrentPath() {
		return System.getProperty("user.dir");
	}
}

// get loacal machine name & OS version
class LocalMachineInfo {
	// get OS Name & Version
	public String getOSInfo() {
		Properties properties = System.getProperties();
		// Set<String> osinfo = properties.stringPropertyNames(); //get java
		// info & OS info
		String OSName = properties.getProperty("os.name");
		String OSVersion = properties.getProperty("os.version");
		String OSinfo = OSName + "-" + OSVersion;
		return OSinfo;
	}

	public String getOSName() {
		Properties properties = System.getProperties();
		// Set<String> osinfo = properties.stringPropertyNames(); //get java
		// info & OS info
		String OSName = properties.getProperty("os.name");
		return OSName;
	}

	// get HostName
	public String getHostname() {
		InetAddress netAddress = getInetAddress();
		// String hostip=getHostIp(netAddress);
		String hostname = getHostName(netAddress);
		return hostname;
	}

	private static InetAddress getInetAddress() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("unknown host!");
		}
		return null;
	}

	// private static String getHostIp(InetAddress netAddress){
	// if(null == netAddress){
	// return null;
	// }
	// String ip = netAddress.getHostAddress(); //get the ip address
	// return ip;
	// }
	private static String getHostName(InetAddress netAddress) {
		if (null == netAddress) {
			return null;
		}
		String name = netAddress.getHostName(); // get the host address
		return name;
	}

}

