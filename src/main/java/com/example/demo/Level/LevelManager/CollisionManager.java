package com.example.demo.Level.LevelManager;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Actor.Plane.UserPlane;

import java.util.List;

/**
 * Manages the collision detection and handling for various actors in the game.
 * This includes collisions between projectiles, enemy units, the user plane, and collectible items.
 * The class provides methods for detecting and handling collisions between different entities
 * such as projectiles, enemy units, and the user plane, and also manages the consequences of these collisions
 * (e.g., taking damage, upgrading projectiles, or picking up items).
 */
public class CollisionManager {

    /**
     * Default constructor for the CollisionManager class.
     * This constructor is provided explicitly, but does not perform any initialization as all methods are static.
     */
    public CollisionManager() {
        // No initialization required
    }

    /**
     * Handles collisions between two lists of actors, applying damage to both colliding actors.
     * If two actors collide, both will take damage.
     *
     * @param actors1 the first list of actors to check for collisions
     * @param actors2 the second list of actors to check for collisions
     */
    public static void handleCollisions(List<ActiveActorDestructible> actors1,
                                        List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                }
            }
        }
    }

    /**
     * Handles collisions between user projectiles and enemy units.
     * This method calls the handleCollisions method with the user projectiles and enemy units lists.
     * Both colliding projectiles and enemy units will take damage.
     *
     * @param userProjectiles the list of projectiles fired by the user
     * @param enemyUnits the list of enemy units
     */
    public static void handleUserProjectileCollisions(List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemyUnits) {
        handleCollisions(userProjectiles, enemyUnits);
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     * This method calls the handleCollisions method with the enemy projectiles and friendly units lists.
     * Both colliding projectiles and friendly units will take damage.
     *
     * @param enemyProjectiles the list of projectiles fired by enemies
     * @param friendlyUnits the list of friendly units
     */
    public static void handleEnemyProjectileCollisions(List<ActiveActorDestructible> enemyProjectiles, List<ActiveActorDestructible> friendlyUnits) {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    /**
     * Handles collisions between the user plane and ammo boxes.
     * If a collision is detected, it triggers an upgrade for the user's projectiles
     * and plays an audio cue to indicate the pickup.
     *
     * @param userPlane the user's plane
     * @param ammoBoxes the list of ammo boxes
     */
    public static void handleUserPlaneAndAmmoBoxCollisions(ActiveActorDestructible userPlane, List<ActiveActorDestructible> ammoBoxes) {
        for (ActiveActorDestructible ammoBox : ammoBoxes) {
            if (checkCollision(userPlane, ammoBox)) {
                handleAmmoBoxPickup(userPlane, ammoBox);
                AudioManager.getInstance().triggerGetAmmoBoxAudio();
            }
        }
    }

    /**
     * Handles the pickup of an ammo box by the user plane, upgrading the user's projectiles.
     *
     * @param userPlane the user's plane
     * @param ammoBox the ammo box being picked up
     */
    public static void handleAmmoBoxPickup(ActiveActorDestructible userPlane, ActiveActorDestructible ammoBox) {
        if (!ammoBox.isDestroyed()) {
            if (userPlane instanceof UserPlane) {
                ((UserPlane) userPlane).upgradeProjectile();
            }
            ammoBox.destroy();
        }
    }

    /**
     * Handles collisions between the user plane and heart items.
     * If a collision is detected, it triggers an increase in the user's health
     * and plays an audio cue to indicate the pickup.
     *
     * @param userPlane the user's plane
     * @param hearts the list of hearts
     */
    public static void handleUserPlaneAndHeartCollisions(ActiveActorDestructible userPlane, List<ActiveActorDestructible> hearts) {
        for (ActiveActorDestructible heart : hearts) {
            if (checkCollision(userPlane, heart)) {
                handleHeartPickup(userPlane, heart);
                AudioManager.getInstance().triggerGetHeartAudio();
            }
        }
    }

    /**
     * Handles the pickup of a heart item by the user plane, increasing the user's health.
     *
     * @param userPlane the user's plane
     * @param heart the heart being picked up
     */
    public static void handleHeartPickup(ActiveActorDestructible userPlane, ActiveActorDestructible heart) {
        if (!heart.isDestroyed()) {
            if (userPlane instanceof UserPlane) {
                ((UserPlane) userPlane).increaseHealth();
            }
            heart.destroy();
        }
    }

    /**
     * Checks if two actors are colliding by comparing their bounds in the parent scene.
     * The collision is determined based on whether the bounding boxes of the two actors intersect.
     *
     * @param actor1 the first actor to check
     * @param actor2 the second actor to check
     * @return true if the actors are colliding, false otherwise
     */
    private static boolean checkCollision(ActiveActorDestructible actor1, ActiveActorDestructible actor2) {
        return actor1.getBoundsInParent().intersects(actor2.getBoundsInParent());
    }
}