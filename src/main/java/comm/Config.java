package comm;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.testng.Assert;

/**
 * 获取env.properties的值
 * @author fei.zhang
 * @date 2017年4月10日
 */
public class Config {

	private static final Config instance = new Config();
	private static final String resource = "env.properties";
	private static String env = null;
	private Properties props = new Properties();//实例化一个Properties对象

	public Properties getProps() {
		return props;
	}

	Config() {
		try {
			// String env = System.getProperty("environment");
		/*	if (env == null) {
				env = System.getProperty("environment");
			}
			Assert.assertNotNull(env, "没有获取到environment的值！");*/
			//加载properties文件  .\\
			props.load(new FileInputStream(".\\src\\test\\resources\\env.properties"));
/*			props.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(env + "/" + resource),
					"UTF-8"));*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getProp(String propName) {
		try {
			return instance.getProps().getProperty(propName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
