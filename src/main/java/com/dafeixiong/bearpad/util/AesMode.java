package com.dafeixiong.bearpad.util;

/**
 * <p>项目名称：dafeixiong-framework-parent</p>
 * <p>类名：com.dafeixiong.framework.util.security.AesMode</p>
 * <p>创建时间: 2022-08-11 14:47 </p>
 * <p>修改时间: 2022-08-11 14:47 </p>
 * AES加密模式枚举<br>
 * 加密模式：加解密算法（AES）/工作模式（ECB/CBC）/填充方式（PKCS5Padding/NoPadding）<br>
 * CBC是向量模式，该模式必须传入偏移量IV，否则会报错<br>
 * ECB是电码本模式，该模式不能传入偏移量IV，否则会报错<br>
 *
 * @author zhang.taowei
 * @version 1.0.0
 */
public enum AesMode {

    /**
     * AES/ECB/PKCS5Padding
     */
    AES_ECB_PKCS5Padding("AES/ECB/PKCS5Padding"),
    /**
     * AES/ECB/NoPadding
     */
    AES_ECB_NoPadding("AES/ECB/NoPadding"),
    /**
     * AES/ECB/PKCS7Padding
     */
    AES_ECB_PKCS7Padding("AES/ECB/PKCS7Padding"),
    /**
     * AES/CBC/PKCS5Padding
     */
    AES_CBC_PKCS5Padding("AES/CBC/PKCS5Padding"),
    /**
     * AES/CBC/PKCS7Padding
     */
    AES_CBC_PKCS7Padding("AES/CBC/PKCS7Padding"),
    /**
     * AES/CBC/NoPadding
     */
    AES_CBC_NoPadding("AES/CBC/NoPadding");

    private final String mode;

    AesMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }
}
