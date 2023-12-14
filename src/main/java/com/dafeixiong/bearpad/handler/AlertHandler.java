package com.dafeixiong.bearpad.handler;

import javafx.scene.control.Alert;

/**
 * <p>项目名称：bear-pad</p>
 * <p>类名：com.dafeixiong.bearpad.handler.AlertHandler</p>
 * <p>创建时间: 2023-08-18 9:57 </p>
 * <p>修改时间: 2023-08-18 9:57 </p>
 * 弹框警告处理器
 *
 * @author zhang.taowei
 * @version 1.0.0
 */
public class AlertHandler {

    private AlertHandler() {

    }

    /**
     * 弹出错误提示框
     * @param e
     */
    public static void showAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("错误");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    /**
     * 弹出警告提示框
     * @param alertContent
     */
    public static void showAlert(String alertContent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警告");
        alert.setHeaderText(null);
        alert.setContentText(alertContent);
        alert.showAndWait();
    }
}
