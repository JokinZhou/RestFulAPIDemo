package comm;

import java.util.List;


/**
 * 提供读取测试数据的方法
 * @author fei.zhang
 * @date 2017年4月10日
 */
public class TestData {

	/**
	 * 初始化测试数据
	 */
	public static void initTestData() {

		List<TestDataInfo> xmlList = XmlUtils.getDataObject();
		CONST.testDataInfos = xmlList;
		if (CONST.testDataInfos.isEmpty()) {
			System.out.println("没有测试数据");
			System.exit(1);
		}
	}

}
