package com.xdchen.netty.client.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


public class Md5Util {
	private static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	//加密
	protected static StringBuffer toHex(byte[] bytes) {
		StringBuffer str = new StringBuffer();
		int length = bytes.length;
		for (int i = 0; i < length; i++) {
			// 转化为16进制
			str.append(Character.forDigit((bytes[i] & 0xf0) >> 4, 16));// (bytes[i] & 0xf0) >> 4 先与0xf0相与取高四位，并右移四位
			str.append(Character.forDigit((bytes[i] & 0x0f), 16));// bytes[i] & 0x0f 与0x0f相与取低四位
		}
		bytes = null;
		return str;
	}
	
	/**
	 * 计算结果和DigestUtils.md5Hex一样(commons-codec)
	 * @param str
	 * @return
	 * @author Stone
	 */
	public static String md5(String str) {
		StringBuffer buffer=null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			//重置摘要
			md5.reset();
			//更新摘要 
			md5.update(str.getBytes());
			//计算摘要 
			byte messageDigest[] = md5.digest();
			buffer = toHex(messageDigest);
		} catch (NoSuchAlgorithmException e) {
		}
		return buffer.toString();
	}
	
	public static String getRandomStr() {
		int len = 6;// 产生多少位的随机字符串
		String ret = "";
		for (int i = 0; i < len; i++) {
			int randomInt = new Random().nextInt(16);
			ret += hexDigits[randomInt];
		}
		return ret;
	}
	
	/**
	 * 
	 * @param originPasswd
	 * @return 经过MD5加密的密码
	 */
	public static String getMd5Passwd(String originPasswd, String salt) {
		return md5(md5(originPasswd) + salt);
	}
	
	public static void main(String[] args) {
		String salt = getRandomStr();// 数据库中的盐
		String encrypt = "3a6ad28973f4096af1f4af06da23aa7a";// 数据库中的密文密码
		
		String password = "091111";// 猜测的明文密码
		String genEncrypt = getMd5Passwd(password, salt);
		System.out.println(genEncrypt);
		System.out.println("salt是:"+ salt+"明文密码是：" + password + " 吗？ " + (encrypt.equals(genEncrypt)));
		
		System.out.println(md5("1#2#3#4"));
	}


}
