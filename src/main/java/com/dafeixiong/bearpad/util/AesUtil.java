package com.dafeixiong.bearpad.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

/**
 * <p>项目名称：dafeixiong-framework-parent</p>
 * <p>类名：com.dafeixiong.framework.util.security.AesUtil</p>
 * <p>创建时间: 2022-08-11 14:47 </p>
 * <p>修改时间: 2022-08-11 14:47 </p>
 * AES加解密工具类
 *
 * @author zhang.taowei
 * @version 1.0.0
 */
public final class AesUtil {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private AesUtil() { }

    /**
     * 加密的参数校验方法
     * @param aesModel 加密模式
     * @param iv 偏移量参数
     * @param securityKey 加密密钥
     * @param content 待加密的内容
     * @throws InvalidAlgorithmParameterException
     */
    private static void checkParameter(String aesModel, String iv, String securityKey, String content)
            throws InvalidAlgorithmParameterException, InvalidKeyException {
        if (aesModel.contains("CBC")) {
            // CBC模式必须传入 偏移量参数-IV，且IV长度应该为16byte
            if (StringUtil.isEmpty(iv) || iv.getBytes(StandardCharsets.UTF_8).length != 16) {
                throw new InvalidAlgorithmParameterException("CBC模式必须传入 长度为 16 bytes 的偏移量参数-IV！");
            }
        } else if (aesModel.contains("ECB")) {
            // ECB模式不能传入 偏移量参数-IV
            if (StringUtil.isNotEmpty(iv)) {
                throw new InvalidAlgorithmParameterException("ECB模式不能传入 偏移量参数-IV！");
            }
        }
        if (aesModel.contains("NoPadding")) {
            // 选择NoPadding模式时，待加密的明文长度必须是16的倍数
            if (content.getBytes(StandardCharsets.UTF_8).length % 16 != 0) {
                throw new InvalidAlgorithmParameterException("NoPadding模式 待加密的明文长度必须是16的倍数！");
            }
        }
        if (securityKey.getBytes().length % 16 != 0) {
            throw new InvalidKeyException("Invalid AES key length: " + securityKey.getBytes().length + " bytes, AES key length must be 16 bytes long!");
        }
    }

    /**
     * 当传入的密钥字符串不符合规定长度时，使用默认生成原始对称秘钥的方法
     * 使用 SHA1PRNG 方法生成原始的 AES 对称秘钥字节数组
     * @param securityKey 用户的秘钥
     * @return AES 对称秘钥字节数组
     * @throws NoSuchAlgorithmException 可能产生的异常类型
     */
    private static byte[] initKeyGenerator(String securityKey) throws NoSuchAlgorithmException {
        // 1.构造密钥生成器,指定为AES算法,不区分大小写
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        // 2.根据ecnodeRules规则初始化密钥生成器
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(securityKey.getBytes());
        // 生成一个128位的随机源,根据传入的字节数组
        keygen.init(128, secureRandom);
        // 3.产生原始对称密钥,获得原始对称密钥的字节数组
        return keygen.generateKey().getEncoded();
    }

    /**
     * AES加密通用方法，将传入的内容加密成byte数组
     * @param content 待加密的明文
     * @param securityKey 128位的密钥字节数组
     * @param iv 偏移量字符串 CBC模式不能为空
     * @param aesModel 加密模式
     * @return AES加密后的byte数组
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private static byte[] encode(String content, byte[] securityKey, String iv, String aesModel)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 根据指定算法AES自成密码器 (加解密算法/工作模式/填充方式)
        Cipher cipher = Cipher.getInstance(aesModel);
        // 根据指定密钥的字节数组生成AES密钥
        SecretKeySpec keySpec = new SecretKeySpec(securityKey, "AES");
        // 判断传入的偏移量参数是否为空进行不同的密码器初始化操作
        if (StringUtil.isNotEmpty(iv)) {
            // 根据指定的偏移量生成对应的对象
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv.getBytes());
            // 初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的密钥KEY，第三个参数为偏移量IV
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
        } else {
            // 初始化密码器,第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作,第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        }
        // 获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        // 根据密码器的初始化方式--加密：将数据加密
        return cipher.doFinal(contentBytes);
    }

    /**
     * 获取AES解密密码器的通用方法，将传入的内容解密成字符串
     * @param securityKey 128位的密钥字节数组
     * @param iv 偏移量字符串 CBC模式不能为空
     * @param aesModel 加密模式
     * @return AES解密的密码器对象
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    private static Cipher decryptCipher(byte[] securityKey, String iv, String aesModel)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // 根据指定算法AES自成密码器 (加解密算法/工作模式/填充方式)
        Cipher cipher = Cipher.getInstance(aesModel);
        // 根据指定密钥的字节数组生成AES密钥
        SecretKeySpec keySpec = new SecretKeySpec(securityKey, "AES");
        // 判断传入的偏移量参数是否为空进行不同的密码器初始化操作
        if (StringUtil.isNotEmpty(iv)) {
            // 根据指定的偏移量生成对应的对象
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv.getBytes());
            // 初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的密钥KEY，第三个参数为偏移量IV
            cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
        } else {
            // 初始化密码器,第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作,第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
        }
        return cipher;
    }

    /**
     * AES解密（URL不安全的）
     * @param content 待解密的密文字符串
     * @param securityKey 指定的密钥
     * @param iv 指定的偏移量字符串
     * @param aesModel 加密模式
     * @return 解密后的字符串
     */
    private static String decryptByAes(String content, String securityKey, String iv, String aesModel) {
        String result = "";
        try {
            byte[] key = securityKey.getBytes(StandardCharsets.UTF_8);
            try {
                // 模式参数校验
                checkParameter(aesModel, iv, securityKey, content);
            } catch (InvalidKeyException e) {
                key = initKeyGenerator(securityKey);
            }
            // 获取对应的解密密码器对象
            Cipher cipher = decryptCipher(key, iv, aesModel);
            // 将加密并编码后的内容通过BASE64解码成字节数组
            byte[] decodeBytes = cipher.doFinal(Base64.getDecoder().decode(content));
            // 将解密后的数据转为字符串，返回解密后的内容
            result = new String(decodeBytes, StandardCharsets.UTF_8);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                NoSuchAlgorithmException | BadPaddingException | InvalidKeyException | IllegalArgumentException e) {
            throw new SecurityException("AES解密失败", e);
        }
        return result;
    }

    /**
     * AES解密（URL安全的）
     * @param content 待解密的密文字符串
     * @param securityKey 指定的密钥
     * @param iv 指定的偏移量字符串
     * @param aesModel 加密模式
     * @return 解密后的字符串
     */
    private static String decryptByAesUrlSafe(String content, String securityKey, String iv, String aesModel) {
        String result = "";
        try {
            byte[] key = securityKey.getBytes(StandardCharsets.UTF_8);
            try {
                // 模式参数校验
                checkParameter(aesModel, iv, securityKey, content);
            } catch (InvalidKeyException e) {
                key = initKeyGenerator(securityKey);
            }
            // 获取对应的解密密码器对象
            Cipher cipher = decryptCipher(key, iv, aesModel);
            // 将加密并编码后的内容通过BASE64解码成字节数组
            byte[] decodeBytes = cipher.doFinal(Base64.getUrlDecoder().decode(content));
            // 将解密后的数据转为字符串，返回解密后的内容
            result = new String(decodeBytes, StandardCharsets.UTF_8);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                NoSuchAlgorithmException | BadPaddingException | InvalidKeyException | IllegalArgumentException e) {
            throw new SecurityException("AES解密失败", e);
        }
        return result;
    }

    /**
     * AES加密（URL不安全的）
     * @param content 待加密的明文
     * @param securityKey 加密使用的密钥字符串
     * @param iv 偏移量字符串
     * @param aesModel 加密模式
     * @return AES加密后的字符串
     */
    private static String encryptByAes(String content, String securityKey, String iv, String aesModel) {
        try {
            byte[] key = securityKey.getBytes(StandardCharsets.UTF_8);
            try {
                // 模式参数校验
                checkParameter(aesModel, iv, securityKey, content);
            } catch (InvalidKeyException e) {
                key = initKeyGenerator(securityKey);
            }
            // 获取AES加密后的byte数组
            byte[] aesBytes = encode(content, key, iv, aesModel);
            // 将加密后的数据转换为BASE64标准的字符串,将加密结果字符串返回
            return Base64.getEncoder().encodeToString(aesBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException |
                IllegalBlockSizeException | InvalidAlgorithmParameterException | IllegalArgumentException e) {
            throw new SecurityException("AES加密失败", e);
        }
    }

    /**
     * AES加密（URL安全的）
     * @param content 待加密的明文
     * @param securityKey 加密使用的密钥字符串
     * @param iv 偏移量字符串
     * @param aesModel 加密模式
     * @return AES加密后的字符串
     */
    private static String encryptByAesUrlSafe(String content, String securityKey, String iv, String aesModel) {
        try {
            byte[] key = securityKey.getBytes(StandardCharsets.UTF_8);
            try {
                // 模式参数校验
                checkParameter(aesModel, iv, securityKey, content);
            } catch (InvalidKeyException e) {
                key = initKeyGenerator(securityKey);
            }
            // 获取AES加密后的byte数组
            byte[] aesBytes = encode(content, key, iv, aesModel);
            // 将加密后的数据转换为BASE64标准的字符串,将加密结果字符串返回
            return Base64.getUrlEncoder().encodeToString(aesBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException |
                IllegalBlockSizeException | InvalidAlgorithmParameterException | IllegalArgumentException e) {
            throw new SecurityException("AES加密失败", e);
        }
    }

    /**
     * AES加密
     * @param content 待加密的明文
     * @param securityKey 秘钥生成规则
     * @param iv 偏移量字符串
     * @param aesMode 加密模式 (加解密算法/工作模式/填充方式)
     * @return 加密后的字符串
     */
    public static String encode(String content, String securityKey, String iv, AesMode aesMode) {
        return encryptByAes(content, securityKey, iv, aesMode.getMode());
    }

    /**
     * AES加密（URL安全的）
     * @param content 待加密的明文
     * @param securityKey 秘钥生成规则
     * @param iv 偏移量字符串
     * @param aesMode 加密模式 (加解密算法/工作模式/填充方式)
     * @return 加密后的字符串
     */
    public static String encodeUrlSafe(String content, String securityKey, String iv, AesMode aesMode) {
        return encryptByAesUrlSafe(content, securityKey, iv, aesMode.getMode());
    }

    /**
     * AES解密
     * @param content 待解密的密文
     * @param securityKey 秘钥生成规则
     * @param iv 偏移量字符串
     * @param aesMode 加密模式 (加解密算法/工作模式/填充方式)
     * @return 解密后的字符串
     */
    public static String decode(String content, String securityKey, String iv, AesMode aesMode) {
        return decryptByAes(content, securityKey, iv, aesMode.getMode());
    }

    /**
     * AES解密（URL安全的）
     * @param content 待解密的密文
     * @param securityKey 秘钥生成规则
     * @param iv 偏移量字符串
     * @param aesMode 加密模式 (加解密算法/工作模式/填充方式)
     * @return 解密后的字符串
     */
    public static String decodeUrlSafe(String content, String securityKey, String iv, AesMode aesMode) {
        return decryptByAesUrlSafe(content, securityKey, iv, aesMode.getMode());
    }
}
