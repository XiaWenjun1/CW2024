package com.example.demo.Level.LevelManager;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Object.FighterPlane;
import com.example.demo.Object.Object.AmmoBox;
import com.example.demo.Object.Object.Heart;
import com.example.demo.Object.UserPlane.UserPlane;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.List;
import java.util.Random;

public class ActorSpawnerManager {
    private final ActiveActorManager activeActorManager;
    private final UserPlane user;
    private final Group root;
    private final Random random;

    public ActorSpawnerManager(ActiveActorManager activeActorManager, UserPlane user, Group root) {
        this.activeActorManager = activeActorManager;
        this.user = user;
        this.root = root;
        this.random = new Random();
    }

    public void updateActors() {
        activeActorManager.updateActors();
        generateEnemyFire();
        spawnRandomItems();
        addUserPlaneHitbox();
    }

    private void addUserPlaneHitbox() {
        if (user != null && user.getHitbox() != null) {
            Node userHitbox = user.getHitbox();
            if (!root.getChildren().contains(userHitbox)) {
                root.getChildren().add(userHitbox);
                userHitbox.toFront();
            }
        }
    }

    public void generateEnemyFire() {
        activeActorManager.getEnemyUnits().forEach(enemy -> {
            if (enemy instanceof FighterPlane) {
                List<ActiveActorDestructible> projectiles = ((FighterPlane) enemy).fireProjectiles();
                if (projectiles != null) {
                    projectiles.forEach(this::spawnEnemyProjectile);
                }
            }
        });
    }

    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            root.getChildren().add(projectile);
            activeActorManager.getEnemyProjectiles().add(projectile);
        }
    }

    public void spawnRandomItems() {
        spawnRandomAmmoBox();
        spawnRandomHeart();
    }

    private void spawnRandomAmmoBox() {
        if (random.nextDouble() < AmmoBox.getSpawnProbability()) {
            double randomX = random.nextDouble(AmmoBox.getMaximumXPosition());
            double randomY = random.nextDouble(AmmoBox.getMaximumYPosition());
            AmmoBox ammoBox = new AmmoBox(randomX, randomY);
            addAmmoBox(ammoBox);
        }
    }

    private void spawnRandomHeart() {
        if (random.nextDouble() < Heart.getSpawnProbability()) {
            double randomX = random.nextDouble(Heart.getMaximumXPosition());
            double randomY = random.nextDouble(Heart.getMaximumYPosition());
            Heart heart = new Heart(randomX, randomY);
            addHeart(heart);
        }
    }

    private void addActorToScene(ActiveActorDestructible actor, List<ActiveActorDestructible> actorList) {
        actorList.add(actor);
        root.getChildren().add(actor);
        Node hitbox = actor.getHitbox();
        root.getChildren().add(hitbox);
    }

    private void addAmmoBox(AmmoBox ammoBox) {
        addActorToScene(ammoBox, activeActorManager.getAmmoBoxes());
    }

    private void addHeart(Heart heart) {
        addActorToScene(heart, activeActorManager.getHearts());
    }

    public void addEnemyUnit(ActiveActorDestructible enemy) {
        addActorToScene(enemy, activeActorManager.getEnemyUnits());
    }
}