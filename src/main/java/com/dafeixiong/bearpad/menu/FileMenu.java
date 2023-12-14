package com.dafeixiong.bearpad.menu;

import com.dafeixiong.bearpad.handler.AlertHandler;
import com.dafeixiong.bearpad.handler.FileHandler;
import com.dafeixiong.bearpad.GlobalValue;
import com.dafeixiong.bearpad.exception.BearPadException;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;

/**
 * <p>项目名称：bear-pad</p>
 * <p>类名：com.dafeixiong.bearpad.menu.FileMenu</p>
 * <p>创建时间: 2023-08-18 9:43 </p>
 * <p>修改时间: 2023-08-18 9:43 </p>
 * 文件操作菜单
 *
 * @author zhang.taowei
 * @version 1.0.0
 */
public class FileMenu {

    private FileMenu() {

    }

    /**
     * 初始化文件菜单
     * @param stage
     * @return
     */
    public static Menu initFileMenu(Stage stage) {
        Menu fileMenu = new Menu("文件");
        MenuItem openMenuItem = new MenuItem("打开");
        // 设置 打开 快捷键 ctrl+O
        openMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCodeCombination.CONTROL_DOWN));
        // 设置打开文件菜单项的事件处理程序
        openMenuItem.setOnAction(event -> {
            GlobalValue.currentFile = GlobalValue.fileChooser.showOpenDialog(stage);
            if (GlobalValue.currentFile != null) {
                try {
                    FileHandler.openFile(GlobalValue.currentFile);
                    stage.setTitle(GlobalValue.title + " " + GlobalValue.currentFile.getAbsolutePath());
                } catch (BearPadException e) {
                    AlertHandler.showAlert(e.getMessage());
                }
            }
        });
        MenuItem saveMenuItem = new MenuItem("保存");
        // 设置 保存 快捷键 ctrl+S
        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN));
        // 设置保存文件菜单项的事件处理程序
        saveMenuItem.setOnAction(event -> {
            if (GlobalValue.currentFile != null) {
                try {
                    FileHandler.saveFile(GlobalValue.currentFile);
                } catch (BearPadException e) {
                    AlertHandler.showAlert(e.getMessage());
                }
            } else {
                saveToFile(stage);
            }
        });
        MenuItem saveOtherMenuItem = new MenuItem("另存为");
        // 设置保存文件菜单项的事件处理程序
        saveOtherMenuItem.setOnAction(event -> {
            saveToFile(stage);
        });
        // 将菜单项添加到菜单中
        fileMenu.getItems().addAll(openMenuItem, saveMenuItem, saveOtherMenuItem);
        return fileMenu;
    }

    /**
     * 另存为文件
     * @param stage
     */
    private static void saveToFile(Stage stage) {
        GlobalValue.currentFile = GlobalValue.fileChooser.showSaveDialog(stage);
        if (GlobalValue.currentFile != null) {
            try {
                FileHandler.saveFile(GlobalValue.currentFile);
                stage.setTitle(GlobalValue.title + " " + GlobalValue.currentFile.getAbsolutePath());
            } catch (BearPadException e) {
                AlertHandler.showAlert(e.getMessage());
            }
        }
    }
}
