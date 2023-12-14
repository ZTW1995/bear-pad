package com.dafeixiong.bearpad.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>项目名称：dafeixiong-framework-parent</p>
 * <p>类名：com.dafeixiong.framework.util.security.Md5Util</p>
 * <p>创建时间: 2022-08-11 14:47 </p>
 * <p>修改时间: 2022-08-11 14:47 </p>
 * MD5摘要工具
 *
 * @author zhang.taowei
 * @version 1.0.0
 */
public final class Md5Util {

    private Md5Util() { }

    /**
     * 对指定字节数组内容进行MD5加密(32位 小写)
     * @param bytes 指定字节数组内容
     * @return 32位小写MD5加密字符串
     */
    public static String toMd5(byte[] bytes) {
        byte[] digest;
        StringBuilder hexValue = new StringBuilder();
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(bytes);
            for (byte b : digest) {
                int val = ((int) b) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException("MD5加密失败", e);
        }
        return hexValue.toString().toLowerCase();
    }

    /**
     * 对指定内容进行MD5加密(32位 小写)
     * @param content 指定内容字符串
     * @return MD5加密字符串
     */
    private static String toMd5(String content) {
        return toMd5(content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取指定字符串的32位小写MD5加密结果字符串
     * @param content 指定内容字符串
     * @return 32位小写MD5加密结果
     */
    public static String getMd5Lower32(String content) {
        return toMd5(content);
    }

    /**
     * 获取指定字符串的16位小写MD5加密结果字符串
     * @param content 指定内容字符串
     * @return 16位小写MD5加密结果
     */
    public static String getMd5Lower16(String content) {
        return toMd5(content).substring(8,24);
    }
}
