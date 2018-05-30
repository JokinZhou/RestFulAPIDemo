package comm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 执行js方法
 * @author fei.zhang
 * @date 2017年4月19日
 */
public class ExcuteJs {

	/**
	 * 执行js
	 * @param functionName  js函数名称
	 * @param paras  js函数的参数
	 * @return
	 * @throws ScriptException
	 * @throws FileNotFoundException
	 * @throws NoSuchMethodException
	 */
	public static String excuteJs(String js, String functionName, Object... paras)
			throws ScriptException, FileNotFoundException, NoSuchMethodException {
		ScriptEngineManager engineManager = new ScriptEngineManager();
		ScriptEngine engine = engineManager.getEngineByName("JavaScript"); // 得到脚本引擎
		engine.eval(new java.io.FileReader(js));
		Invocable inv = (Invocable) engine;
		Object a = inv.invokeFunction(functionName, paras);
		return a.toString();
	}

	public static void main(String[] args) throws FileNotFoundException, NoSuchMethodException, ScriptException {

		System.out.println(excuteJs("src/test/resources/wechat.js", "getNowFormatDate", "0"));

	}

}

