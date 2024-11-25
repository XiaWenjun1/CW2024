package com.example.demo.Level.LevelManager;

import com.example.demo.Actor.ActiveActorDestructible;

import java.util.ArrayList;
import java.util.List;

public class ActiveActorManager {
    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;
    private final List<ActiveActorDestructible> ammoBoxes;
    private final List<ActiveActorDestructible> hearts;

    public ActiveActorManager() {
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        this.ammoBoxes = new ArrayList<>();
        this.hearts = new ArrayList<>();
    }

    public void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
        ammoBoxes.forEach(box -> box.updateActor());
        hearts.forEach(heart -> heart.updateActor());
    }

    public List<ActiveActorDestructible> getAllActors() {
        List<ActiveActorDestructible> allActors = new ArrayList<>();
        allActors.addAll(friendlyUnits);
        allActors.addAll(enemyUnits);
        allActors.addAll(userProjectiles);
        allActors.addAll(enemyProjectiles);
        allActors.addAll(ammoBoxes);
        allActors.addAll(hearts);
        return allActors;
    }

    public List<ActiveActorDestructible> getFriendlyUnits() {
        return friendlyUnits;
    }

    public List<ActiveActorDestructible> getEnemyUnits() {
        return enemyUnits;
    }

    public List<ActiveActorDestructible> getUserProjectiles() {
        return userProjectiles;
    }

    public List<ActiveActorDestructible> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    public List<ActiveActorDestructible> getAmmoBoxes() { return ammoBoxes; }

    public List<ActiveActorDestructible> getHearts() {
        return hearts;
    }
}
