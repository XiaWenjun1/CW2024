package com.example.demo.Level.LevelManager;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.FighterPlane;
import com.example.demo.Object.Object.AmmoBox;
import com.example.demo.Object.Object.Heart;
import com.example.demo.Actor.UserPlane.UserPlane;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.List;
import java.util.Random;

/**
 * Manages the spawning of various actors (such as enemies, projectiles, ammo boxes, and hearts) in the game.
 * It also updates the state of active actors and manages user plane hitbox.
 */
public class ActorSpawnerManager {
    private final ActiveActorManager activeActorManager;
    private final UserPlane user;
    private final Group root;
    private final Random random;

    /**
     * Constructs an instance of ActorSpawnerManager.
     *
     * @param activeActorManager the ActiveActorManager that manages all active actors in the game
     * @param user the UserPlane representing the player's plane
     * @param root the root Group where all actors are added to the scene
     */
    public ActorSpawnerManager(ActiveActorManager activeActorManager, UserPlane user, Group root) {
        this.activeActorManager = activeActorManager;
        this.user = user;
        this.root = root;
        this.random = new Random();
    }

    /**
     * Updates the state of all actors, generates enemy fire, spawns random items (ammo boxes and hearts),
     * and ensures the user's plane hitbox is present in the scene.
     */
    public void updateActors() {
        activeActorManager.updateActors();
        generateEnemyFire();
        spawnRandomItems();
        addUserPlaneHitbox();
    }

    /**
     * Ensures the user's plane hitbox is added to the scene, if not already present.
     */
    private void addUserPlaneHitbox() {
        if (user != null && user.getHitbox() != null) {
            Node userHitbox = user.getHitbox();
            if (!root.getChildren().contains(userHitbox)) {
                root.getChildren().add(userHitbox);
                userHitbox.toFront();
            }
        }
    }

    /**
     * Generates enemy fire by iterating through enemy units (such as fighter planes) and spawning projectiles.
     */
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

    /**
     * Spawns an enemy projectile and adds it to the scene.
     *
     * @param projectile the enemy projectile to spawn
     */
    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            root.getChildren().add(projectile);
            activeActorManager.getEnemyProjectiles().add(projectile);
        }
    }

    /**
     * Spawns random items in the game, such as ammo boxes and hearts.
     */
    public void spawnRandomItems() {
        spawnRandomAmmoBox();
        spawnRandomHeart();
    }

    /**
     * Spawns a random ammo box based on its spawn probability and adds it to the scene.
     */
    private void spawnRandomAmmoBox() {
        if (random.nextDouble() < AmmoBox.getSpawnProbability()) {
            double randomX = random.nextDouble(AmmoBox.getMaximumXPosition());
            double randomY = random.nextDouble(AmmoBox.getMaximumYPosition()) + 20;
            AmmoBox ammoBox = new AmmoBox(randomX, randomY);
            addAmmoBox(ammoBox);
        }
    }

    /**
     * Spawns a random heart based on its spawn probability and adds it to the scene.
     */
    private void spawnRandomHeart() {
        if (random.nextDouble() < Heart.getSpawnProbability()) {
            double randomX = random.nextDouble(Heart.getMaximumXPosition());
            double randomY = random.nextDouble(Heart.getMaximumYPosition()) + 20;
            Heart heart = new Heart(randomX, randomY);
            addHeart(heart);
        }
    }

    /**
     * Adds an actor to both the scene and the corresponding actor list in ActiveActorManager.
     *
     * @param actor the actor to add to the scene
     * @param actorList the list of actors to add the actor to
     */
    private void addActorToScene(ActiveActorDestructible actor, List<ActiveActorDestructible> actorList) {
        actorList.add(actor);
        root.getChildren().add(actor);
        Node hitbox = actor.getHitbox();
        root.getChildren().add(hitbox);
    }

    /**
     * Adds an ammo box to the scene and the ActiveActorManager's list of ammo boxes.
     *
     * @param ammoBox the ammo box to add
     */
    private void addAmmoBox(AmmoBox ammoBox) {
        addActorToScene(ammoBox, activeActorManager.getAmmoBoxes());
    }

    /**
     * Adds a heart to the scene and the ActiveActorManager's list of hearts.
     *
     * @param heart the heart to add
     */
    private void addHeart(Heart heart) {
        addActorToScene(heart, activeActorManager.getHearts());
    }

    /**
     * Adds an enemy unit to the scene and the ActiveActorManager's list of enemy units.
     *
     * @param enemy the enemy unit to add
     */
    public void addEnemyUnit(ActiveActorDestructible enemy) {
        addActorToScene(enemy, activeActorManager.getEnemyUnits());
    }
}