package comm;
/**
 * 存取测试数据
 * @author fei.zhang
 *
 */

public class TestDataInfo {
	private String xmlName;// xml文件名
	private String name;// 节点属性名称
	private String value;// 节点值

	/**
	 * @return the xmlName
	 */
	public String getXmlName() {
		return xmlName;
	}

	/**
	 * @param xmlName
	 *            the xmlName to set
	 */
	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "xmlName:" + xmlName + ",name:" + name + ",value:" + value;
	}

}
