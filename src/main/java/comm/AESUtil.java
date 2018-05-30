package comm;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESUtil {

	public static String Encrypt(String sSrc, String sKey, String sIv) throws Exception {
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
		IvParameterSpec iv = new IvParameterSpec(sIv.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes());
		byte[] encrypted2 = Base64.encodeBase64(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
		return new String(encrypted2);
	}

	// 解密
	public static String Decrypt(String sSrc, String sKey, String sIv) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(sIv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new Base64().decode(sSrc.getBytes());// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original);
			return originalString;
		} catch (Exception ex) {
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		/*
		 * 此处使用AES-128-CBC加密模式，key需要为16位。
		 */
		String cKey = "wZTc98PWEMqqCSCs";
		// 需要加密的字串
		String cSrc = "123QWEasd";
		System.out.println(cSrc);
		// 加密
		String enString = AESUtil.Encrypt(cSrc, cKey, "0102030405060708");
		// enString = "2Ei4n9Hb3r44MR35atBgE5Pojozh+n0qMbMkH++MVrM=
		// ASd121111111111111111";
		System.out.println("加密后的字串是：" + enString);
		// 解密
		String DeString = AESUtil.Decrypt(enString, cKey, "0102030405060708");
		// String DeString = AESUtil.Decrypt("l01okxX18X5zT8ZflqGhoA==",
		// cKey, "0102030405060708");
		System.out.println("解密后的字串是：" + DeString);
	}

}

