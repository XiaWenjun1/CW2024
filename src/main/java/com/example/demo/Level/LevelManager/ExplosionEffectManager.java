package com.example.demo.Level.LevelManager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

public class ExplosionEffectManager {

    private static final String EXPLOSION_SOUND_PATH = "/com/example/demo/sounds/explosion.mp3";
    private static final String EXPLOSION_IMAGE_PATH = "/com/example/demo/images/explosion.png";

    private static AudioClip explosionSound = new AudioClip(
            ExplosionEffectManager.class.getResource(EXPLOSION_SOUND_PATH).toExternalForm());
    private static boolean explosionSoundEnabled = true;

    public static void triggerExplosionEffect(Group root, Node actor) {
        if (actor == null || root == null) return;

        double x = actor.localToScene(actor.getBoundsInLocal()).getMinX();
        double y = actor.localToScene(actor.getBoundsInLocal()).getMinY();

        ImageView explosionImage = new ImageView(new Image(
                ExplosionEffectManager.class.getResource(EXPLOSION_IMAGE_PATH).toExternalForm()));
        explosionImage.setFitWidth(150);
        explosionImage.setFitHeight(150);
        explosionImage.setX(x);
        explosionImage.setY(y);

        root.getChildren().add(explosionImage);

        Timeline removeExplosion = new Timeline(new KeyFrame(Duration.seconds(1), e -> root.getChildren().remove(explosionImage)));
        removeExplosion.setCycleCount(1);
        removeExplosion.play();

        if (explosionSoundEnabled) {
            explosionSound.play();
        }
    }

    public static void setExplosionSoundEnabled(boolean enabled) {
        explosionSoundEnabled = enabled;
    }

    public static boolean isExplosionSoundEnabled() {
        return explosionSoundEnabled;
    }
}