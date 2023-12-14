package com.dafeixiong.bearpad.util;

/**
 * <p>项目名称：dafeixiong-framework-parent</p>
 * <p>类名：com.dafeixiong.framework.util.StringUtil</p>
 * <p>创建时间: 2022-08-11 14:47 </p>
 * <p>修改时间: 2022-08-11 14:47 </p>
 * 字符串相关工具类
 *
 * @author zhang.taowei
 * @version 1.0.0
 */
public final class StringUtil {

    private StringUtil() { }

    /**
     * 判断字符串是否为空
     * @param str 字符串
     * @return 为空-true 不为空-false
     */
    public static boolean isEmpty(final Object str) {
        return null == str || "".equals(str);
    }

    /**
     * 判断一个字符串是否不为空
     * @param str 字符串
     * @return 不为空-true 为空-false
     */
    public static boolean isNotEmpty(final Object str) {
        return !isEmpty(str);
    }

    /**
     * 获取字符串长度
     * 支持 String StringBuffer StringBuilder 三种类型
     * @param str 字符串对象
     * @return 字符串的长度
     */
    public static long getLength(Object str) {
        if (str instanceof String) {
            return ((String) str).length();
        }
        else if (str instanceof StringBuffer) {
            return ((StringBuffer) str).length();
        }
        else if (str instanceof StringBuilder) {
            return ((StringBuilder) str).length();
        }
        return 0;
    }

    /**
     * 将指定的字符串首字母转小写
     * @param str 需要首字母转小写的字符串
     * @return 转换后的字符串
     */
    public static String firstToLower(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 获取 参数-source 中，包含几个 统计目标字符串-chars
     * @param source 源字符串
     * @param chars 统计目标字符串
     * @return
     */
    public static int countChars(String source, String chars) {
        int count = 0;
        if (!source.contains(chars)) {
            return count;
        }
        while(source.contains(chars)){
            source = source.substring(source.indexOf(chars) + chars.length());
            count++;
        }
        return count;
    }

    /**
     * 将字符串中指定位置的字符替换成指定的其他字符
     * @param content 原始字符串
     * @param index 要替换的字符下标
     * @param replaceChar 替换后的字符
     * @return 替换完成的字符串
     */
    public static String replace(String content, int index, char replaceChar) {
        if (index > content.length()-1 || index < 0) {
            throw new RuntimeException("替换的下标不在原始字符串长度范围内！");
        }
        // 获取要替换的位置前面和后面的字符串
        String prefix = content.substring(0, index);
        String suffix = content.substring(index + 1);
        // 执行替换并输出结果
        return prefix + replaceChar + suffix;
    }
}
