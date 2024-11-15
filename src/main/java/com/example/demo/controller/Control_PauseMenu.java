package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import com.example.demo.Level.LevelParent;

public class Control_PauseMenu {

    private LevelParent levelParent;  // 引用游戏类

    @FXML
    private AnchorPane pauseMenuPane;  // FXML 中的根容器

    @FXML
    private Button continueButton;

    private Pane mainRoot;  // 主界面容器，用于应用虚化效果

    public void initialize(LevelParent levelParent) {
        this.levelParent = levelParent;

        // 确保暂停菜单一开始是不可见的
        pauseMenuPane.setVisible(false);
        continueButton.setVisible(false);
    }


    // 设置主界面容器
    public void setMainRoot(Pane mainRoot) {
        this.mainRoot = mainRoot;
    }

    public void showPauseMenu() {
        boolean isVisible = pauseMenuPane.isVisible(); // 判断当前菜单是否已经显示
        pauseMenuPane.setVisible(!isVisible);  // 如果已经显示，就隐藏它，否则显示
        continueButton.setVisible(!isVisible); // 显示或隐藏继续按钮
    }

    // 继续游戏
    @FXML
    private void handleContinue() {
        levelParent.continueGame();  // 恢复游戏
        pauseMenuPane.setVisible(false);  // 隐藏暂停界面

        // 隐藏按钮
        continueButton.setVisible(false);  // 隐藏继续按钮

        // 移除虚化效果
        if (mainRoot != null) {
            mainRoot.setEffect(null);  // 移除虚化效果
        }
    }
}