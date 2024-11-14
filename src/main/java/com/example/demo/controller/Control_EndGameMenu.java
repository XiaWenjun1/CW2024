package com.example.demo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import com.example.demo.LevelParent;
import javafx.application.Platform;

import java.io.IOException;

public class Control_EndGameMenu {

    @FXML
    private Button returnToMainButton;

    @FXML
    private Button exitButton;

    private LevelParent levelParent; // 引用 LevelParent，用于返回主界面

    // 初始化控制器
    public void initialize(LevelParent levelParent) {
        this.levelParent = levelParent;
    }

    @FXML
    private void handleReturnToMainButton(ActionEvent event) {
        // 清理当前的游戏资源
        levelParent.cleanUp();

        // 加载主界面
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Main/Main.fxml"));
            Parent mainRoot = loader.load();
            Scene mainScene = new Scene(mainRoot, 1300, 750);  // 设置 Scene 大小

            Stage stage = (Stage) returnToMainButton.getScene().getWindow();

            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 点击 "Exit" 按钮时的处理
    @FXML
    private void handleExitButton(ActionEvent event) {
        // 退出游戏
        Platform.exit();
    }
}