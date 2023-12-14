package com.dafeixiong.bearpad.menu;

import com.dafeixiong.bearpad.GlobalValue;
import com.dafeixiong.bearpad.util.Md5Util;
import com.dafeixiong.bearpad.util.StringUtil;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

/**
 * <p>项目名称：bear-pad</p>
 * <p>类名：com.dafeixiong.bearpad.menu.SecurityMenu</p>
 * <p>创建时间: 2023-08-18 9:59 </p>
 * <p>修改时间: 2023-08-18 9:59 </p>
 * 安全设置菜单
 *
 * @author zhang.taowei
 * @version 1.0.0
 */
public class SecurityMenu {

    private SecurityMenu() {

    }

    /**
     * 弹出输入密钥框的界面
     */
    public static void requestSecurityKey() {
        TextInputDialog securityKeyInput = new TextInputDialog();
        securityKeyInput.setTitle("设置密钥");
        securityKeyInput.setHeaderText(StringUtil.isEmpty(GlobalValue.securityKey) ? null : "当前密钥: " + GlobalValue.securityKey);
        securityKeyInput.setContentText("密钥: ");
        // 显示对话框并等待用户输入
        securityKeyInput.showAndWait().ifPresent(key -> {
            GlobalValue.securityKey = key;
            GlobalValue.iv = Md5Util.getMd5Lower16(GlobalValue.securityKey);
        });
    }

    /**
     * 初始化安全菜单
     * @return
     */
    public static Menu initSecurityMenu() {
        Menu securityMenu = new Menu("安全");
        MenuItem setSecurityMenuItem = new MenuItem("设置密钥");
        // 设置 密钥 快捷键 ctrl+K
        setSecurityMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.K, KeyCodeCombination.CONTROL_DOWN));
        // 设置 密钥菜单项的事件处理程序
        setSecurityMenuItem.setOnAction(event -> {
            requestSecurityKey();
        });
        securityMenu.getItems().addAll(setSecurityMenuItem);
        return securityMenu;
    }
}
