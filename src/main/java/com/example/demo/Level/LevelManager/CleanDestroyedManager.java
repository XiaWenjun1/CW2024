package com.example.demo.Level.LevelManager;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Object.Boundary;
import com.example.demo.Object.FighterPlane;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.function.Consumer;

public class CleanDestroyedManager {
    private final Group root;
    private final ActiveActorManager activeActorManager;

    public CleanDestroyedManager(Group root, ActiveActorManager activeActorManager) {
        this.root = root;
        this.activeActorManager = activeActorManager;
    }

    public void cleanObj() {
        Boundary RIGHT_BOUNDARY = Boundary.createRightBoundary();
        Boundary LEFT_BOUNDARY = Boundary.createLeftBoundary();

        cleanObjects(
                activeActorManager.getUserProjectiles(),
                activeActorManager.getEnemyProjectiles(),
                activeActorManager.getAmmoBoxes(),
                activeActorManager.getHearts(),
                RIGHT_BOUNDARY,
                LEFT_BOUNDARY,
                this::removeActorFromScene
        );
    }

    public void cleanObjects(
            List<ActiveActorDestructible> userProjectiles,
            List<ActiveActorDestructible> enemyProjectiles,
            List<ActiveActorDestructible> ammoBoxes,
            List<ActiveActorDestructible> hearts,
            Boundary rightBoundary,
            Boundary leftBoundary,
            Consumer<ActiveActorDestructible> removeActorFromScene
    ) {
        // Simplified by using a helper method to handle different types of objects
        cleanList(userProjectiles, rightBoundary, removeActorFromScene);
        cleanList(enemyProjectiles, leftBoundary, removeActorFromScene);
        cleanList(ammoBoxes, rightBoundary, leftBoundary, removeActorFromScene);
        cleanList(hearts, rightBoundary, leftBoundary, removeActorFromScene);
    }

    private void cleanList(List<ActiveActorDestructible> actors, Boundary boundary, Consumer<ActiveActorDestructible> removeActorFromScene) {
        actors.removeIf(actor -> {
            if (checkCollision(actor.getHitbox(), boundary)) {
                processActor(actor, removeActorFromScene);
                return true; // Mark for removal
            }
            return false;
        });
    }

    private void cleanList(List<ActiveActorDestructible> actors, Boundary boundary1, Boundary boundary2, Consumer<ActiveActorDestructible> removeActorFromScene) {
        actors.removeIf(actor -> {
            if (checkCollision(actor.getHitbox(), boundary1) || checkCollision(actor.getHitbox(), boundary2)) {
                processActor(actor, removeActorFromScene);
                return true; // Mark for removal
            }
            return false;
        });
    }

    private boolean checkCollision(Rectangle rect1, Boundary boundary) {
        return rect1.getBoundsInParent().intersects(boundary.getBoundsInParent());
    }

    private void processActor(ActiveActorDestructible actor, Consumer<ActiveActorDestructible> removeActorFromScene) {
        actor.destroy();
        removeActorFromScene.accept(actor);
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
        // Use removeIf to filter and remove destroyed actors in one pass
        actors.removeIf(actor -> {
            if (actor.isDestroyed()) {
                if (actor instanceof FighterPlane) {
                    ExplosionEffectManager.triggerExplosionEffect(root, (FighterPlane) actor);
                }
                removeActorFromScene(actor);
                return true; // Mark for removal
            }
            return false;
        });
    }

    private void removeActorFromScene(ActiveActorDestructible actor) {
        root.getChildren().remove(actor);
        root.getChildren().remove(actor.getHitbox());
    }
}