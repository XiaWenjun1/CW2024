package com.example.demo.Level;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Object.Boundary;
import com.example.demo.Object.FighterPlane;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class CleanDestroyedManager {
    private Group root;
    private ActiveActorManager activeActorManager;

    public CleanDestroyedManager(Group root, ActiveActorManager activeActorManager) {
        this.root = root;
        this.activeActorManager = activeActorManager;
    }

    public void cleanObj() {
        Boundary RIGHT_BOUNDARY = new Boundary(1350, 0, 1, 1000);
        Boundary LEFT_BOUNDARY = new Boundary(-50, 0, 1, 1000);

        cleanObjects(
                activeActorManager.getUserProjectiles(),
                activeActorManager.getEnemyProjectiles(),
                activeActorManager.getAmmoBoxes(),
                activeActorManager.getHearts(),
                RIGHT_BOUNDARY,
                LEFT_BOUNDARY,
                this::removeActorFromScene,
                CleanDestroyedManager::checkCollision
        );
    }

    public static void cleanObjects(
            List<ActiveActorDestructible> userProjectiles,
            List<ActiveActorDestructible> enemyProjectiles,
            List<ActiveActorDestructible> ammoBoxes,
            List<ActiveActorDestructible> hearts,
            Boundary rightBoundary,
            Boundary leftBoundary,
            Consumer<ActiveActorDestructible> removeActorFromScene,
            BiPredicate<Rectangle, Rectangle> checkCollision) {

        Iterator<ActiveActorDestructible> userIterator = userProjectiles.iterator();
        while (userIterator.hasNext()) {
            ActiveActorDestructible bullet = userIterator.next();
            if (checkCollision.test(bullet.getHitbox(), rightBoundary)) {
                bullet.destroy();
                removeActorFromScene.accept(bullet);
                userIterator.remove();
            }
        }

        Iterator<ActiveActorDestructible> enemyIterator = enemyProjectiles.iterator();
        while (enemyIterator.hasNext()) {
            ActiveActorDestructible bullet = enemyIterator.next();
            if (checkCollision.test(bullet.getHitbox(), leftBoundary)) {
                bullet.destroy();
                removeActorFromScene.accept(bullet);
                enemyIterator.remove();
            }
        }

        Iterator<ActiveActorDestructible> ammoBoxIterator = ammoBoxes.iterator();
        while (ammoBoxIterator.hasNext()) {
            ActiveActorDestructible ammoBox = ammoBoxIterator.next();
            if (checkCollision.test(ammoBox.getHitbox(), rightBoundary) || checkCollision.test(ammoBox.getHitbox(), leftBoundary)) {
                removeActorFromScene.accept(ammoBox);
                ammoBoxIterator.remove();
            }
        }

        Iterator<ActiveActorDestructible> heartIterator = hearts.iterator();
        while (heartIterator.hasNext()) {
            ActiveActorDestructible heart = heartIterator.next();
            if (checkCollision.test(heart.getHitbox(), rightBoundary) || checkCollision.test(heart.getHitbox(), leftBoundary)) {
                removeActorFromScene.accept(heart);
                heartIterator.remove();
            }
        }
    }

    public static boolean checkCollision(Rectangle rect1, Rectangle rect2) {
        return rect1.getBoundsInParent().intersects(rect2.getBoundsInParent());
    }

    public void removeAllDestroyedActors() {
        List<List<ActiveActorDestructible>> actorGroups = List.of(
                activeActorManager.getFriendlyUnits(),
                activeActorManager.getEnemyUnits(),
                activeActorManager.getUserProjectiles(),
                activeActorManager.getEnemyProjectiles(),
                activeActorManager.getAmmoBoxes(),
                activeActorManager.getHearts()
        );

        actorGroups.forEach(this::removeDestroyedActors);
    }

    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream()
                .filter(ActiveActorDestructible::isDestroyed)
                .toList();

        destroyedActors.forEach(actor -> {
            actor.destroy();
            if (actor instanceof FighterPlane) {
                ExplosionEffectManager.triggerExplosionEffect(root, (FighterPlane) actor);
            }
            removeActorFromScene(actor);
        });

        actors.removeAll(destroyedActors);
    }

    private void removeActorFromScene(ActiveActorDestructible actor) {
        root.getChildren().remove(actor);
        root.getChildren().remove(actor.getHitbox());
    }
}