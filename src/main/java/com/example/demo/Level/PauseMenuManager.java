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
    private List<ActiveActorDestructible> hearts;

    public PauseMenuManager(Timeline timeline, Scene scene, UserPlane user, Node background,
                            List<ActiveActorDestructible> friendlyUnits, List<ActiveActorDestructible> enemyUnits,
                            List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemyProjectiles,
                            List<ActiveActorDestructible> ammoBoxes, List<ActiveActorDestructible> hearts) {
        this.timeline = timeline;
        this.scene = scene;
        this.user = user;
        this.background = background;
        this.friendlyUnits = friendlyUnits;
        this.enemyUnits = enemyUnits;
        this.userProjectiles = userProjectiles;
        this.enemyProjectiles = enemyProjectiles;
        this.ammoBoxes = ammoBoxes;
        this.hearts = hearts;
    }

    public void loadPauseMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/PauseMenu/PauseMenu.fxml"));
            pauseMenuRoot = loader.load();
            controlPauseMenu = loader.getController();
            controlPauseMenu.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void togglePause() {
        if (isPaused) {
            continueGame();
        } else {
            pauseGame();
        }
    }

    public void pauseGame() {
        isPaused = true;
        timeline.stop();
        user.setPaused(true);

        GaussianBlur blur = new GaussianBlur(10);
        background.setEffect(blur);
        applyBlurToActiveActors(10);

        controlPauseMenu.showPauseMenu();
    }

    public void continueGame() {
        isPaused = false;
        timeline.play();
        user.setPaused(false);
        background.setEffect(null);
        removeBlurFromActiveActors();
        controlPauseMenu.hidePauseMenu();
    }

    private void applyBlurToActiveActors(double radius) {
        List<ActiveActorDestructible> allActors = new ArrayList<>();
        allActors.addAll(friendlyUnits);
        allActors.addAll(enemyUnits);
        allActors.addAll(userProjectiles);
        allActors.addAll(enemyProjectiles);
        allActors.addAll(ammoBoxes);
        allActors.addAll(hearts);

        for (ActiveActorDestructible actor : allActors) {
            if (actor instanceof Node) {
                ((Node) actor).setEffect(new GaussianBlur(radius));
            }

            if (actor instanceof Boss) {
                Boss boss = (Boss) actor;

                if (boss.getHealthBar() != null) {
                    boss.getHealthBar().setEffect(new GaussianBlur(radius));
                }
                if (boss.getShieldImage() != null) {
                    boss.getShieldImage().setEffect(new GaussianBlur(radius));
                }
            }
        }
    }

    private void removeBlurFromActiveActors() {
        List<ActiveActorDestructible> allActors = new ArrayList<>();
        allActors.addAll(friendlyUnits);
        allActors.addAll(enemyUnits);
        allActors.addAll(userProjectiles);
        allActors.addAll(enemyProjectiles);
        allActors.addAll(ammoBoxes);
        allActors.addAll(hearts);

        for (ActiveActorDestructible actor : allActors) {
            if (actor instanceof Node) {
                ((Node) actor).setEffect(null);
            }

            if (actor instanceof Boss) {
                Boss boss = (Boss) actor;
                if (boss.getHealthBar() != null) {
                    boss.getHealthBar().setEffect(null);
                }

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
