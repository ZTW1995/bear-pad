package com.dafeixiong.bearpad;

import com.dafeixiong.bearpad.menu.FileMenu;
import com.dafeixiong.bearpad.menu.SecurityMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * <p>项目名称：bear-pad</p>
 * <p>类名：com.dafeixiong.bearpad.App</p>
 * <p>创建时间: 2023-08-17 15:12 </p>
 * <p>修改时间: 2023-08-17 15:12 </p>
 *
 * @author zhang.taowei
 * @version 1.0.0
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(GlobalValue.title);
        initFileChooser();
        initTextArea();
        MenuBar menuBar = createMenuBar(primaryStage);
        // 创建根布局并设置菜单栏和文本区域
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(GlobalValue.textArea);
        // 创建场景并设置根布局
        Scene scene = new Scene(root, 800, 600);
        // 设置主舞台的场景
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 创建菜单栏
     * @param primaryStage 主舞台
     * @return
     */
    private MenuBar createMenuBar(Stage primaryStage) {
        // 创建菜单和菜单项
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = FileMenu.initFileMenu(primaryStage);
        Menu securityMenu = SecurityMenu.initSecurityMenu();
        // 将菜单添加到菜单栏中
        menuBar.getMenus().addAll(fileMenu, securityMenu);
        return menuBar;
    }

    /**
     * 初始化文件选择器
     */
    private void initFileChooser() {
        // 创建文件选择器
        GlobalValue.fileChooser = new FileChooser();
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("Files (*.bear, *.txt)", "*.bear", "*.txt");
        FileChooser.ExtensionFilter bearFilter = new FileChooser.ExtensionFilter("Files (*.bear)", "*.bear");
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Files (*.txt)", "*.txt");
        GlobalValue.fileChooser.getExtensionFilters().add(allFilter);
        GlobalValue.fileChooser.getExtensionFilters().add(bearFilter);
        GlobalValue.fileChooser.getExtensionFilters().add(txtFilter);
    }

    /**
     * 初始化文本域
     */
    private void initTextArea() {
        // 创建文本区域，并设置显示的字体大小
        GlobalValue.textArea = new TextArea();
        Font font = new Font(20);
        GlobalValue.textArea.setFont(font);
    }

}
