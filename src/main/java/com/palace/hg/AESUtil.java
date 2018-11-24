package com.palace.hg;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

	private static String strDefSkey = "456789!@#$QWERMM";
	private static String strDefsIv = "456789!QAZ@WSXMM";
	public static String encrypt(String datagram) throws Exception {
		return encrypt(datagram,strDefsIv,strDefsIv);
	}
	public static String encrypt(String datagram,String sKey,String sIv) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(sKey.getBytes(),"AES"),new IvParameterSpec(sIv.getBytes()));
		byte[] encrypted = cipher.doFinal(datagram.getBytes());
		return new String(org.apache.commons.codec.binary.Base64.encodeBase64(encrypted));
	}
	public static String decrypt(String datagram) throws Exception {
		return decrypt(datagram,strDefsIv,strDefsIv);
	}
	public static String decrypt(String datagram,String sKey,String sIv) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sKey.getBytes(),"AES"),new IvParameterSpec(sIv.getBytes()));
		byte[] baseRes = org.apache.commons.codec.binary.Base64.decodeBase64(datagram);
		return new String(cipher.doFinal(baseRes));
	}
	
	public static void main(String[] args) throws Exception {
		String ss = encrypt("hg20182018");
		System.out.println(ss);
	}
}
