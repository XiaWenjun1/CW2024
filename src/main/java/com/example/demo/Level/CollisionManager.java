package com.example.demo.Level;

import com.example.demo.Actor.ActiveActor;
import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Object.Boundary;
import com.example.demo.Object.UserPlane;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class CollisionManager {

    // 处理两个列表中的碰撞
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

    // 处理用户子弹和敌人之间的碰撞
    public static void handleUserProjectileCollisions(List<ActiveActorDestructible> userProjectiles, List<ActiveActorDestructible> enemyUnits) {
        handleCollisions(userProjectiles, enemyUnits);
    }

    // 处理敌人子弹和友军飞机之间的碰撞
    public static void handleEnemyProjectileCollisions(List<ActiveActorDestructible> enemyProjectiles, List<ActiveActorDestructible> friendlyUnits) {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    // 处理用户飞机和物品箱子之间的碰撞
    public static void handleUserPlaneAndAmmoBoxCollisions(ActiveActorDestructible userPlane, List<ActiveActorDestructible> ammoBoxes) {
        for (ActiveActorDestructible ammoBox : ammoBoxes) {
            if (userPlane.checkCollision(ammoBox)) {
                handleAmmoBoxPickup(userPlane, ammoBox);
            }
        }
    }

    private static void handleAmmoBoxPickup(ActiveActorDestructible userPlane, ActiveActorDestructible ammoBox) {
        if (!ammoBox.isDestroyed()) { // 确保箱子未被销毁
            if (userPlane instanceof UserPlane) {
                ((UserPlane) userPlane).upgradeProjectile();
            }
            ammoBox.destroy(); // 设置箱子为已销毁状态
        }
    }

    public static void cleanObjects(
            List<ActiveActorDestructible> userProjectiles,
            List<ActiveActorDestructible> enemyProjectiles,
            List<ActiveActorDestructible> ammoBoxes,
            Boundary rightBoundary,
            Boundary leftBoundary,
            Consumer<ActiveActorDestructible> removeActorFromScene,
            BiPredicate<Rectangle, Rectangle> checkCollision) {

        // 清理用户子弹
        Iterator<ActiveActorDestructible> userIterator = userProjectiles.iterator();
        while (userIterator.hasNext()) {
            ActiveActorDestructible bullet = userIterator.next();
            if (checkCollision.test(bullet.getHitbox(), rightBoundary)) {
                bullet.destroy();
                removeActorFromScene.accept(bullet);
                userIterator.remove();
            }
        }

        // 清理敌人子弹
        Iterator<ActiveActorDestructible> enemyIterator = enemyProjectiles.iterator();
        while (enemyIterator.hasNext()) {
            ActiveActorDestructible bullet = enemyIterator.next();
            if (checkCollision.test(bullet.getHitbox(), leftBoundary)) {
                bullet.destroy();
                removeActorFromScene.accept(bullet);
                enemyIterator.remove();
            }
        }

        // 清理弹药箱
        Iterator<ActiveActorDestructible> ammoBoxIterator = ammoBoxes.iterator();
        while (ammoBoxIterator.hasNext()) {
            ActiveActorDestructible ammoBox = ammoBoxIterator.next();
            if (checkCollision.test(ammoBox.getHitbox(), rightBoundary) || checkCollision.test(ammoBox.getHitbox(), leftBoundary)) {
                removeActorFromScene.accept(ammoBox);
                ammoBoxIterator.remove();
            }
        }
    }

    public static boolean checkCollision(Rectangle rect1, Rectangle rect2) {
        return rect1.getBoundsInParent().intersects(rect2.getBoundsInParent());
    }
}