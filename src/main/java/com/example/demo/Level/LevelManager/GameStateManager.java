package com.example.demo.Level.LevelManager;

import com.example.demo.Display.LevelView;
import com.example.demo.Object.UserPlane;

public class GameStateManager {
    private final UserPlane user;
    private final LevelView levelView;
    private final ActiveActorManager activeActorManager;
    private int currentNumberOfEnemies;
    private int previousNumberOfEnemies;

    public GameStateManager(UserPlane user, LevelView levelView, ActiveActorManager activeActorManager) {
        this.user = user;
        this.levelView = levelView;
        this.activeActorManager = activeActorManager;
        this.currentNumberOfEnemies = 0;
        this.previousNumberOfEnemies = 0;
    }

    public void updateStatus() {
        updateNumberOfEnemies();
        updateKillCount();
        updateLevelView();
    }

    private void updateLevelView() {
        levelView.removeHearts(user.getHealth());
        levelView.addHearts(user.getHealth());
    }

    private void updateNumberOfEnemies() {
        previousNumberOfEnemies = currentNumberOfEnemies;
        currentNumberOfEnemies = activeActorManager.getEnemyUnits().size();
    }

    private void updateKillCount() {
        int defeatedEnemies = previousNumberOfEnemies - currentNumberOfEnemies;
        for (int i = 0; i < defeatedEnemies; i++) {
            user.incrementKillCount();
        }
    }
}