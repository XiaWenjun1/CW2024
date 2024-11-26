package com.example.demo.Level.LevelManager;

import com.example.demo.Actor.ActiveActorDestructible;
import com.example.demo.Object.Boundary;
import com.example.demo.Actor.FighterPlane;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.function.Consumer;

/**
 * Manages the cleanup of destroyed actors in the game, removing them from the scene
 * when they go out of bounds or are marked as destroyed.
 */
public class CleanDestroyedManager {
    private final Group root;
    private final ActiveActorManager activeActorManager;

    /**
     * Constructs an instance of CleanDestroyedManager.
     *
     * @param root the root Group where the actors are displayed
     * @param activeActorManager the manager responsible for managing active actors
     */
    public CleanDestroyedManager(Group root, ActiveActorManager activeActorManager) {
        this.root = root;
        this.activeActorManager = activeActorManager;
    }

    /**
     * Cleans up objects that are outside the boundaries or destroyed.
     * This method removes user projectiles, enemy projectiles, ammo boxes, and hearts
     * if they are out of bounds or destroyed.
     */
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

    /**
     * Cleans up various types of game objects, removing them from the scene if they are destroyed or out of bounds.
     *
     * @param userProjectiles the list of user projectiles to clean
     * @param enemyProjectiles the list of enemy projectiles to clean
     * @param ammoBoxes the list of ammo boxes to clean
     * @param hearts the list of hearts to clean
     * @param rightBoundary the boundary on the right side of the screen
     * @param leftBoundary the boundary on the left side of the screen
     * @param removeActorFromScene a function that removes an actor from the scene when necessary
     */
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

    /**
     * Cleans a list of actors by removing them from the scene if they collide with a given boundary.
     *
     * @param actors the list of actors to clean
     * @param boundary the boundary to check against
     * @param removeActorFromScene a function that removes an actor from the scene when necessary
     */
    private void cleanList(List<ActiveActorDestructible> actors, Boundary boundary, Consumer<ActiveActorDestructible> removeActorFromScene) {
        actors.removeIf(actor -> {
            if (checkCollision(actor.getHitbox(), boundary)) {
                processActor(actor, removeActorFromScene);
                return true; // Mark for removal
            }
            return false;
        });
    }

    /**
     * Cleans a list of actors by removing them from the scene if they collide with either of two boundaries.
     *
     * @param actors the list of actors to clean
     * @param boundary1 the first boundary to check against
     * @param boundary2 the second boundary to check against
     * @param removeActorFromScene a function that removes an actor from the scene when necessary
     */
    private void cleanList(List<ActiveActorDestructible> actors, Boundary boundary1, Boundary boundary2, Consumer<ActiveActorDestructible> removeActorFromScene) {
        actors.removeIf(actor -> {
            if (checkCollision(actor.getHitbox(), boundary1) || checkCollision(actor.getHitbox(), boundary2)) {
                processActor(actor, removeActorFromScene);
                return true; // Mark for removal
            }
            return false;
        });
    }

    /**
     * Checks if the given rectangle (hitbox) intersects with the specified boundary.
     *
     * @param rect1 the rectangle to check for intersection
     * @param boundary the boundary to check against
     * @return true if the rectangle intersects with the boundary, false otherwise
     */
    private boolean checkCollision(Rectangle rect1, Boundary boundary) {
        return rect1.getBoundsInParent().intersects(boundary.getBoundsInParent());
    }

    /**
     * Processes an actor that is out of bounds or destroyed, triggering its destruction and removing it from the scene.
     *
     * @param actor the actor to process
     * @param removeActorFromScene a function that removes the actor from the scene
     */
    private void processActor(ActiveActorDestructible actor, Consumer<ActiveActorDestructible> removeActorFromScene) {
        actor.destroy();
        removeActorFromScene.accept(actor);
    }

    /**
     * Removes all destroyed actors from the scene and triggers the explosion effect for any destroyed fighter planes.
     */
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

    /**
     * Removes destroyed actors from a given list, triggering explosion effects for fighter planes.
     *
     * @param actors the list of actors to remove destroyed ones from
     */
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

    /**
     * Removes an actor and its hitbox from the scene.
     *
     * @param actor the actor to remove
     */
    private void removeActorFromScene(ActiveActorDestructible actor) {
        root.getChildren().remove(actor);
        root.getChildren().remove(actor.getHitbox());
    }
}