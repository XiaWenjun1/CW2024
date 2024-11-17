package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Object.Boss;
import com.example.demo.Object.UserPlane;
import com.example.demo.controller.Control_PauseMenu;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;

import java.util.ArrayList;
import java.util.List;

public class PauseMenuManager {

    private Parent pauseMenuRoot;
    private Control_PauseMenu controlPauseMenu;
    private boolean isPaused = false;
    private Timeline timeline;
    private Scene scene;
    private UserPlane user;
    private Node background;
    private List<ActiveActorDestructible> friendlyUnits;
    private List<ActiveActorDestructible> enemyUnits;
    private List<ActiveActorDestructible> userProjectiles;
    private List<ActiveActorDestructible> enemyProjectiles;
    private List<ActiveActorDestructible> ammoBoxes;

    // 构造函数传递必要的依赖
    public PauseMenuManager(Timeline timeline, Scene scene, UserPlane user, Node background,
                            List<ActiveActorDestructible> friendlyUnits, List<ActiveActorDestructible> enemyUnits,
                            List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemyProjectiles,
                            List<ActiveActorDestructible> ammoBoxes) {
        this.timeline = timeline;
        this.scene = scene;
        this.user = user;
        this.background = background;
        this.friendlyUnits = friendlyUnits;
        this.enemyUnits = enemyUnits;
        this.userProjectiles = userProjectiles;
        this.enemyProjectiles = enemyProjectiles;
        this.ammoBoxes = ammoBoxes;
    }

    // 加载暂停菜单
    public void loadPauseMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/PauseMenu/PauseMenu.fxml"));
            pauseMenuRoot = loader.load();
            controlPauseMenu = loader.getController();
            controlPauseMenu.initialize(this);  // 传递PauseMenuManager实例
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 切换暂停和继续
    public void togglePause() {
        if (isPaused) {
            continueGame();
        } else {
            pauseGame();
        }
    }

    // 暂停游戏
    public void pauseGame() {
        isPaused = true;
        timeline.stop(); // 停止游戏循环
        user.setPaused(true);  // 暂停时禁用输入

        // 禁用按键事件
        scene.setOnKeyPressed(event -> {
            // 暂停时不响应任何按键
        });

        // 为背景和所有活跃的 Actor 添加虚化效果
        GaussianBlur blur = new GaussianBlur(10);
        background.setEffect(blur);
        applyBlurToActiveActors(10);  // 给所有 Actor 加上虚化效果

        // 显示暂停菜单
        controlPauseMenu.showPauseMenu();
    }

    // 继续游戏
    public void continueGame() {
        isPaused = false;
        timeline.play(); // 恢复游戏循环
        user.setPaused(false);

        // 移除背景的虚化效果
        background.setEffect(null);

        // 移除ActiveActor的虚化效果
        removeBlurFromActiveActors();

        // 隐藏暂停菜单
        controlPauseMenu.hidePauseMenu(); // 假设有一个方法来隐藏暂停菜单
    }

    // 为所有活跃的 Actor 添加虚化效果
    private void applyBlurToActiveActors(double radius) {
        List<ActiveActorDestructible> allActors = new ArrayList<>();
        allActors.addAll(friendlyUnits);
        allActors.addAll(enemyUnits);
        allActors.addAll(userProjectiles);
        allActors.addAll(enemyProjectiles);
        allActors.addAll(ammoBoxes);

        for (ActiveActorDestructible actor : allActors) {
            if (actor instanceof Node) {
                ((Node) actor).setEffect(new GaussianBlur(radius));
            }

            // 检查是否是Boss对象，并对Boss的血条和护盾应用虚化
            if (actor instanceof Boss) {
                Boss boss = (Boss) actor;

                // 对Boss的血条进行虚化
                if (boss.getHealthBar() != null) {
                    boss.getHealthBar().setEffect(new GaussianBlur(radius));
                }

                // 对Boss的护盾进行虚化
                if (boss.getShieldImage() != null) {
                    boss.getShieldImage().setEffect(new GaussianBlur(radius));
                }
            }
        }
    }

    // 恢复虚化效果的代码（移除虚化）
    private void removeBlurFromActiveActors() {
        List<ActiveActorDestructible> allActors = new ArrayList<>();
        allActors.addAll(friendlyUnits);
        allActors.addAll(enemyUnits);
        allActors.addAll(userProjectiles);
        allActors.addAll(enemyProjectiles);
        allActors.addAll(ammoBoxes);

        for (ActiveActorDestructible actor : allActors) {
            if (actor instanceof Node) {
                ((Node) actor).setEffect(null); // 清除虚化效果
            }

            // 对Boss的血条和护盾移除虚化效果
            if (actor instanceof Boss) {
                Boss boss = (Boss) actor;

                // 移除Boss的血条虚化
                if (boss.getHealthBar() != null) {
                    boss.getHealthBar().setEffect(null);
                }

                // 移除Boss的护盾虚化
                if (boss.getShieldImage() != null) {
                    boss.getShieldImage().setEffect(null);
                }
            }
        }
    }

    public Parent getPauseMenuRoot() {
        return pauseMenuRoot;
    }
}
