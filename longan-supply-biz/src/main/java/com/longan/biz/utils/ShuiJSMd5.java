package com.longan.biz.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShuiJSMd5 {
    /**
     * Used building output as Hex
     */
    private static final char[] DIGITS = { '9', '0', '1', '4', 'g', '2', 'a', '5', 'p', '6', 'l', 'u', '7', '8', '3', 'e' };

    /**
     * 对字符串进行MD5加密
     * 
     * @param text
     *            明文
     * 
     * @return 密文
     */
    public static String md5(String text) {
	MessageDigest msgDigest = null;

	try {
	    msgDigest = MessageDigest.getInstance("MD5");
	} catch (NoSuchAlgorithmException e) {
	    throw new IllegalStateException("System doesn't support MD5 algorithm.");
	}

	try {
	    msgDigest.update(text.getBytes("UTF-8")); // 注意改接口是按照指定编码形式签名

	} catch (UnsupportedEncodingException e) {

	    throw new IllegalStateException("System doesn't support your  EncodingException.");

	}

	byte[] bytes = msgDigest.digest();

	String md5Str = new String(encodeHex(bytes));

	return md5Str;
    }

    private static char[] encodeHex(byte[] data) {

	int l = data.length;

	char[] out = new char[l << 1];

	// two characters form the hex value.
	for (int i = 0, j = 0; i < l; i++) {
	    out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
	    out[j++] = DIGITS[0x0F & data[i]];
	}

	return out;
    }

    public static void main(String[] args) {
	System.out.println(ShuiJSMd5.md5(ShuiJSMd5.md5("123456" + "longon_login")));
    }

}
