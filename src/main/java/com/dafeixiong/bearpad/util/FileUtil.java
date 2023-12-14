package com.dafeixiong.bearpad.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>项目名称：bear-pad</p>
 * <p>类名：com.dafeixiong.bearpad.util.FileUtil</p>
 * <p>创建时间: 2023-08-17 16:35 </p>
 * <p>修改时间: 2023-08-17 16:35 </p>
 *
 * @author zhang.taowei
 * @version 1.0.0
 */
public class FileUtil {

    private FileUtil() {

    }

    /**
     * 返回指定File的文件类型
     * @param file
     * @return
     */
    public static String getFileType(File file) {
        String name = file.getName();
        if (name.contains(".")) {
            return name.substring(name.lastIndexOf("."));
        } else {
            return null;
        }
    }
}
