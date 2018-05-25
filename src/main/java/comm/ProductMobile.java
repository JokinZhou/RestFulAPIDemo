/**
 * 
 */
package comm;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lenovo
 *
 */
public class ProductMobile {

	
	
	public String productMobileNumber(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStr = sdf.format(date);
		System.out.println(timeStr);
		String time = timeStr.substring(4, 8);
		String userName = "ceshi" + time;
		System.out.println(userName);
		String userMobile = "13" + timeStr.substring(5, 14);
		System.out.println(userMobile);
		return userMobile;
		
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProductMobile pm = new ProductMobile();
		pm.productMobileNumber();
		

	}

}
