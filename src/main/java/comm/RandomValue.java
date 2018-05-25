/**
 * 
 */
package comm;

/**
 * @author lenovo
 *
 */
public class RandomValue {
	public static String[] telFirst = "130,131,132,133,134,135,136,137,138,139,147,150,151,152,153,154,155,156,157,158,159,189"
			.split(",");

	public RandomValue() {

	}

	public static int getNum(int start, int end) {
		System.out.println((int) (Math.random() * (end - start + 1) + start));
		return (int) (Math.random() * (end - start + 1) + start);
	}

	// 返回手机号码
	public static String getTel() {
		int index = getNum(0, telFirst.length - 1);
		String first = telFirst[index];
		String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
		String thrid = String.valueOf(getNum(1, 9100) + 10000).substring(1);
		return first + second + thrid;
	}

	public static void main(String[] args) {

		String tel = getTel();
		System.out.println(tel);
	}

}

