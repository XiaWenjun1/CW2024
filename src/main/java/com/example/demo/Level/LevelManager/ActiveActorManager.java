package com.example.demo.Level.LevelManager;

import com.example.demo.Actor.ActiveActorDestructible;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the active actors in the game, including friendly units, enemy units, user projectiles,
 * enemy projectiles, ammo boxes, and hearts. Provides methods to update and access all actors
 * within these categories.
 */
public class ActiveActorManager {
    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;
    private final List<ActiveActorDestructible> ammoBoxes;
    private final List<ActiveActorDestructible> hearts;

    /**
     * Constructs an instance of ActiveActorManager, initializing empty lists for each type of actor.
     */
    public ActiveActorManager() {
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        this.ammoBoxes = new ArrayList<>();
        this.hearts = new ArrayList<>();
    }

    /**
     * Updates all the active actors in the game. Calls the updateActor method on each actor in
     * the lists of friendly units, enemy units, projectiles, ammo boxes, and hearts.
     */
    public void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
        ammoBoxes.forEach(box -> box.updateActor());
        hearts.forEach(heart -> heart.updateActor());
    }

    /**
     * Returns a combined list of all active actors, including friendly units, enemy units,
     * user projectiles, enemy projectiles, ammo boxes, and hearts.
     *
     * @return a list containing all active actors.
     */
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

    /**
     * Returns the list of friendly units (e.g., player-controlled planes).
     *
     * @return a list of friendly units.
     */
    public List<ActiveActorDestructible> getFriendlyUnits() {
        return friendlyUnits;
    }

    /**
     * Returns the list of enemy units (e.g., enemy-controlled planes).
     *
     * @return a list of enemy units.
     */
    public List<ActiveActorDestructible> getEnemyUnits() {
        return enemyUnits;
    }

    /**
     * Returns the list of user projectiles (e.g., bullets fired by the player).
     *
     * @return a list of user projectiles.
     */
    public List<ActiveActorDestructible> getUserProjectiles() {
        return userProjectiles;
    }

    /**
     * Returns the list of enemy projectiles (e.g., bullets fired by the enemies).
     *
     * @return a list of enemy projectiles.
     */
    public List<ActiveActorDestructible> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    /**
     * Returns the list of ammo boxes (e.g., power-ups that replenish ammo).
     *
     * @return a list of ammo boxes.
     */
    public List<ActiveActorDestructible> getAmmoBoxes() {
        return ammoBoxes;
    }

    /**
     * Returns the list of hearts (e.g., health power-ups).
     *
     * @return a list of hearts.
     */
    public List<ActiveActorDestructible> getHearts() {
        return hearts;
    }
}