/**
 * 
 */
package comm;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.testng.Assert;

/**
 * xml文件处理类，xml存储测试数据
 * @author fei.zhang
 * @date 2017年4月10日
 */
public class XmlUtils {

	private final static String attribeName = "name";// xml文件节点属性名称

	private static String env = null;

	public static void main(String[] args) {

		List<TestDataInfo> xmlList = getDataObject();
		CONST.testDataInfos = xmlList;

		if (CONST.testDataInfos.isEmpty()) {
			System.out.println("没有测试数据");
			System.exit(1);
		}
		System.out.println(XmlUtils.getValue("用户名"));
	}

	/**
	 * 获取所有xml文件
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private static List<File> getXmlList() {
		// 通过classpath获取直接获取文件路径
		// String filePath =
		// XmlUtils.class.getClassLoader().getResource(CONST.TEMP_FILE).getPath();
		// TestXml t = new TestXml();

		if (env == null) {
			env = System.getProperty("environment");
		}
		Assert.assertNotNull(env, "没有获取到environment的值！");
		String filePath = XmlUtils.class.getClassLoader().getResource(env + "/" + "testdata").getPath();
		// filePath = filePath.replace(CONSTS.TEMP_FILE, "");
		File dir = new File(filePath);
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		// System.out.println(JSON.toJSON(files));
		if (files != null && files.length > 0) {
			List<File> xmlFiles = new ArrayList<File>();
			for (File file : files) {
				if (file.isFile() && file.getName().substring(file.getName().lastIndexOf(".")).equals(".xml")
						&& !file.getName().contains("testng"))
					xmlFiles.add(file);
			}
			// System.out.println(JSON.toJSON(xmlFiles));
			return xmlFiles;
		}
		return null;
	}

	/**
	 * 读取所有xml数据
	 * 
	 * @return
	 */
	public static List<TestDataInfo> getDataObject() {
		// 1。获取xml list
		List<File> xmlList = getXmlList();
		if (xmlList != null && xmlList.size() >= 0) {
			// 2.读取xml，把所有的数据放在TestDataInfo
			List<TestDataInfo> result = new ArrayList<TestDataInfo>();
			for (File file : xmlList) {
				List<TestDataInfo> item = getDataObjectFromXml(file);
				if (item != null && item.size() > 0) {
					result.addAll(item);
				}
			}
			return result;
		}
		return null;
	}

	/**
	 * 读取单个xml数据
	 * 
	 * @param file
	 * @return
	 */
	private static List<TestDataInfo> getDataObjectFromXml(File file) {
		List<TestDataInfo> result = new ArrayList<TestDataInfo>();
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(file);
			Element root = document.getRootElement();
			List nodes = root.elements();
			for (Iterator it = nodes.iterator(); it.hasNext();) {
				TestDataInfo testDataInfo = new TestDataInfo();
				testDataInfo.setXmlName(file.getName());
				Element elm = (Element) it.next();
				testDataInfo.setName(elm.attribute(attribeName).getValue());
				testDataInfo.setValue(elm.getTextTrim());
				result.add(testDataInfo);
			}
			return result;
		} catch (DocumentException e) {
			return null;
		}
	}

	/**
	 * 根据xml文件名和标签名查找测试数据 
	 * @param xmlName
	 * @param name
	 * @return
	 */
	public static String getValue(String xmlName, String name) {

		if (CONST.testDataInfos != null && CONST.testDataInfos.size() > 0) {
			for (TestDataInfo item : CONST.testDataInfos) {
				if (item.getXmlName().equals(xmlName) && item.getName().equals(name)) {
					return item.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 根据标签名查找测试数据
	 * @param name
	 * @return
	 */
	public static String getValue(String name) {
		if (CONST.testDataInfos != null && CONST.testDataInfos.size() > 0) {
			for (TestDataInfo item : CONST.testDataInfos) {
				if (item.getName().equals(name)) {
					return item.getValue();
				}
			}
		}
		return null;
	}

}
