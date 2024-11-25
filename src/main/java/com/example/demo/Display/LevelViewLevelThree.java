package com.example.demo.Display;

import com.example.demo.Object.Boss.Boss;
import com.example.demo.Object.Boss.BossHealthBar;
import javafx.application.Platform;
import javafx.scene.Group;

public class LevelViewLevelThree extends LevelView {

    private final Group root;
    private final ShieldImage shieldImage;
    private final BossHealthBar bossHealthBar;
    private final TargetLevel hint;
    private final ScoreBoard scoreBoard;

    public LevelViewLevelThree(Group root, int heartsToDisplay, int bossHealth, int initialKills, int targetKills) {
        super(root, heartsToDisplay);
        this.root = root;
        this.shieldImage = new ShieldImage();
        this.bossHealthBar = new BossHealthBar(bossHealth);
        this.hint = new TargetLevel();
        this.scoreBoard = new ScoreBoard(initialKills, targetKills);

        initializeUI();
    }

    private void initializeUI() {
        root.getChildren().addAll(shieldImage, bossHealthBar, hint, scoreBoard);
        Platform.runLater(() -> {
            bossHealthBar.show();
            hint.show();
            scoreBoard.show();
        });
    }

    public void updateShieldPosition(Boss boss) {
        double bossPositionX = boss.getLayoutX() + boss.getTranslateX();
        double bossPositionY = boss.getLayoutY() + boss.getTranslateY();
        shieldImage.setLayout(bossPositionX, bossPositionY);
    }

    public void showShield() {
        shieldImage.show();
    }

    public void hideShield() {
        shieldImage.hide();
    }

    public void updateBossHealthPosition(Boss boss) {
        double bossPositionX = boss.getLayoutX() + boss.getTranslateX();
        double bossPositionY = boss.getLayoutY() + boss.getTranslateY() + 65;
        bossHealthBar.setLayout(bossPositionX, bossPositionY);
    }

    public void updateBossHealth(int bossHealth) {
        bossHealthBar.updateHealth(bossHealth);
    }

    public void hideHealthBar() {
        bossHealthBar.hide();
    }

    // Update the kills on the scoreboard
    public void updateKills(int currentKills, int targetKills) {
        scoreBoard.updateKills(currentKills, targetKills);
    }
}