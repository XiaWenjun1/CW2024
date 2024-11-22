package com.example.demo.Level.LevelManager;

import com.example.demo.Actor.ActiveActor;
import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Object.UserPlane.UserPlane;
import javafx.scene.Node;

import java.util.List;


public class CollisionManager {

    public static void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor instanceof ActiveActor && otherActor instanceof ActiveActor) {
                    Node actorHitbox = ((ActiveActor) actor).getHitbox();
                    Node otherActorHitbox = ((ActiveActor) otherActor).getHitbox();
                    if (actorHitbox.getBoundsInParent().intersects(otherActorHitbox.getBoundsInParent())) {
                        actor.takeDamage();
                        otherActor.takeDamage();
                    }
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
            if (userPlane.checkCollision(ammoBox)) {
                handleAmmoBoxPickup(userPlane, ammoBox);
            }
        }
    }

    private static void handleAmmoBoxPickup(ActiveActorDestructible userPlane, ActiveActorDestructible ammoBox) {
        if (!ammoBox.isDestroyed()) {
            if (userPlane instanceof UserPlane) {
                ((UserPlane) userPlane).upgradeProjectile();
            }
            ammoBox.destroy();
        }
    }

    public static void handleUserPlaneAndHeartCollisions(ActiveActorDestructible userPlane, List<ActiveActorDestructible> hearts) {
        for (ActiveActorDestructible heart : hearts) {
            if (userPlane.checkCollision(heart)) {
                handleHeartPickup(userPlane, heart);
            }
        }
    }

    private static void handleHeartPickup(ActiveActorDestructible userPlane, ActiveActorDestructible heart) {
        if (!heart.isDestroyed()) {
            if (userPlane instanceof UserPlane) {
                ((UserPlane) userPlane).increaseHealth();
            }
            heart.destroy();
        }
    }

}