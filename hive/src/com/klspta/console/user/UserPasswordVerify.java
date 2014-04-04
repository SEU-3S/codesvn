package com.klspta.console.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * <br>
 * Description:用户密码加密解密验证 <br>
 * Author:朱波海 <br>
 * Date:2013-1-24
 * 
 * @param list
 */
public class UserPasswordVerify {
	private static final String Algorithm = "DESede";
	private static final byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58,
			(byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB,
			(byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40,
			0x36, (byte) 0xE2 };
	/**
	 * 
	 * <br>
	 * Description:md5加密 
	 * Author:朱波海 <br>
	 * Date:2013-1-24
	 * 
	 * @param list
	 */

	public static String md5Verify(String str) {
		String s = str;
		if (s == null) {
			return "";
		} else {
			String enconded = null;
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException ex) {
				System.out.println("md5实例化有问题！");
			}
			sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
			try {
				enconded = baseEncoder.encode(md5.digest(s.getBytes("utf-8")));
			} catch (Exception ex) {
			}
			return enconded;
		}
	}

	// DES加密
	/**
	 * 创建密匙
	 * 
	 * @param algorithm 加密算法,可用 DES,DESede,Blowfish
	 * @return SecretKey 秘密（对称）密钥
	 */
	public static SecretKey createSecretKey(String algorithm) {
		// 声明KeyGenerator对象
		KeyGenerator keygen;
		// 声明 密钥对象
		SecretKey deskey = null;
		try {
			// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
			keygen = KeyGenerator.getInstance(algorithm);
			// 生成一个密钥
			deskey = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 返回密匙
		return deskey;
	}

	/**
	 * 根据密匙进行DES加密
	 * 
	 * @param key密匙
	 * @param info要加密的信息
	 * @return String 加密后的信息
	 */
	public static String encryptToDES(SecretKey key, String info) {
		// 定义 加密算法,可用 DES,DESede,Blowfish
		String Algorithm = "DES";
		// 加密随机数生成器 (RNG),(可以不写)
		SecureRandom sr = new SecureRandom();
		// 定义要生成的密文
		byte[] cipherByte = null;
		try {
			// 得到加密/解密器
			Cipher c1 = Cipher.getInstance(Algorithm);
			// 用指定的密钥和模式初始化Cipher对象
			// 参数:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)
			c1.init(Cipher.ENCRYPT_MODE, key, sr);
			// 对要加密的内容进行编码处理,
			cipherByte = c1.doFinal(info.getBytes());
		} catch (Exception e) {
			System.out.println("字符编码出错！");
		}
		// 返回密文的十六进制形式
		return byte2Hex(cipherByte);
	}

	/**
	 * 根据密匙进行DES解密
	 * 
	 * @param key 密匙
	 * @param sInfo要解密的密文
	 * @return String 返回解密后信息
	 */
	public static String decryptByDES(SecretKey key, String sInfo) {
		// 定义 加密算法,
		String Algorithm = "DES";
		// 加密随机数生成器 (RNG)
		SecureRandom sr = new SecureRandom();
		byte[] cipherByte = null;
		try {
			// 得到加密/解密器
			Cipher c1 = Cipher.getInstance(Algorithm);
			// 用指定的密钥和模式初始化Cipher对象
			c1.init(Cipher.DECRYPT_MODE, key, sr);
			// 对要解密的内容进行编码处理
			cipherByte = c1.doFinal(hex2Byte(sInfo));
		} catch (Exception e) {
			System.out.println("字符编码出错！");
		}
		return new String(cipherByte);
	}

	

	public static String encryptMode(String src) {
		try {
			SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return byte2Hex(c1.doFinal(src.getBytes()));
		} catch (Exception e) {
			System.out.println("字符编码出错！");
		}
		return null;
	}

	public static String decryptMode(String src) {
		try {
			SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return new String(c1.doFinal(hex2Byte(src)));
		} catch (Exception e) {
			System.out.println("字符编码出错！");
		}
		return null;
	}
	

	/**
	 * 
	 * <br>
	 * Description:转换处理 Author:朱波海 <br>
	 * Date:2013-1-24
	 * 
	 * @param list
	 */
	public static String byte2Hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2Byte(String str) {
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer
						.decode("0x" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	

}
