package com.example.demo.Level.LevelManager;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Object.UserPlane.UserPlane;

import java.util.List;


public class CollisionManager {

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

    public static void handleUserProjectileCollisions(List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemyUnits) {
        handleCollisions(userProjectiles, enemyUnits);
    }

    public static void handleEnemyProjectileCollisions(List<ActiveActorDestructible> enemyProjectiles, List<ActiveActorDestructible> friendlyUnits) {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    public static void handleUserPlaneAndAmmoBoxCollisions(ActiveActorDestructible userPlane, List<ActiveActorDestructible> ammoBoxes) {
        for (ActiveActorDestructible ammoBox : ammoBoxes) {
            if (checkCollision(userPlane, ammoBox)) {
                handleAmmoBoxPickup(userPlane, ammoBox);
            }
        }
    }

    public static void handleAmmoBoxPickup(ActiveActorDestructible userPlane, ActiveActorDestructible ammoBox) {
        if (!ammoBox.isDestroyed()) {
            if (userPlane instanceof UserPlane) {
                ((UserPlane) userPlane).upgradeProjectile();
            }
            ammoBox.destroy();
        }
    }

    public static void handleUserPlaneAndHeartCollisions(ActiveActorDestructible userPlane, List<ActiveActorDestructible> hearts) {
        for (ActiveActorDestructible heart : hearts) {
            if (checkCollision(userPlane, heart)) {
                handleHeartPickup(userPlane, heart);
            }
        }
    }

    public static void handleHeartPickup(ActiveActorDestructible userPlane, ActiveActorDestructible heart) {
        if (!heart.isDestroyed()) {
            if (userPlane instanceof UserPlane) {
                ((UserPlane) userPlane).increaseHealth();
            }
            heart.destroy();
        }
    }

    private static boolean checkCollision(ActiveActorDestructible actor1, ActiveActorDestructible actor2) {
        return actor1.getBoundsInParent().intersects(actor2.getBoundsInParent());
    }

}