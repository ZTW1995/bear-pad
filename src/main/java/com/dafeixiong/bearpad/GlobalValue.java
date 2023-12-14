package com.dafeixiong.bearpad;

import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * <p>项目名称：bear-pad</p>
 * <p>类名：com.dafeixiong.bearpad.GlobalValue</p>
 * <p>创建时间: 2023-08-18 9:46 </p>
 * <p>修改时间: 2023-08-18 9:46 </p>
 * 全局变量储存类
 *
 * @author zhang.taowei
 * @version 1.0.0
 */
public final class GlobalValue {

    private GlobalValue() {

    }

    public final static String title = "BearPad Editor";

    /**
     * 加密密钥
     */
    public static String securityKey;

    /**
     * 加密偏移量
     */
    public static String iv;

    /**
     * 当前操作的文件
     */
    public static File currentFile;

    /**
     * 文件选择器
     */
    public static FileChooser fileChooser;

    /**
     * 文本域组件
     */
    public static TextArea textArea;

}
