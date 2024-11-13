package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import com.example.demo.LevelParent;
import javafx.stage.Stage;

import java.io.IOException;

public class Control_PauseMenu {

    private LevelParent levelParent;  // 引用游戏类

    @FXML
    private AnchorPane pauseMenuPane;  // FXML 中的根容器

    @FXML
    private Button backButton;

    @FXML
    private Button continueButton;

    private Pane mainRoot;  // 主界面容器，用于应用虚化效果

    public void initialize(LevelParent levelParent) {
        this.levelParent = levelParent;

        // 确保暂停菜单一开始是不可见的
        pauseMenuPane.setVisible(false);
        continueButton.setVisible(false);
        backButton.setVisible(false);
    }


    // 设置主界面容器
    public void setMainRoot(Pane mainRoot) {
        this.mainRoot = mainRoot;
    }

    public void showPauseMenu() {
        boolean isVisible = pauseMenuPane.isVisible(); // 判断当前菜单是否已经显示
        pauseMenuPane.setVisible(!isVisible);  // 如果已经显示，就隐藏它，否则显示
        continueButton.setVisible(!isVisible); // 显示或隐藏继续按钮
        backButton.setVisible(!isVisible);     // 显示或隐藏返回按钮
    }

    // 继续游戏
    @FXML
    private void handleContinue() {
        levelParent.continueGame();  // 恢复游戏
        pauseMenuPane.setVisible(false);  // 隐藏暂停界面

        // 隐藏按钮
        continueButton.setVisible(false);  // 隐藏继续按钮
        backButton.setVisible(false);      // 隐藏返回按钮

        // 移除虚化效果
        if (mainRoot != null) {
            mainRoot.setEffect(null);  // 移除虚化效果
        }
    }

    @FXML
    private void handleBackToMain() {
        try {
            // 加载主界面 (Main.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Main/Main.fxml"));
            Parent mainMenuRoot = loader.load();  // 加载主界面的根节点
            Scene mainMenuScene = new Scene(mainMenuRoot, 1300, 700);  // 创建场景

            // 获取主舞台并设置主界面
            Stage stage = BattlePlane.getPrimaryStage();  // 获取主舞台
            stage.setScene(mainMenuScene);  // 设置新的场景
            stage.show();  // 显示主界面

            // 隐藏暂停菜单和按钮
            pauseMenuPane.setVisible(false);
            continueButton.setVisible(false);
            backButton.setVisible(false);

            // 移除虚化效果
            if (mainRoot != null) {
                mainRoot.setEffect(null);  // 移除虚化效果
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}