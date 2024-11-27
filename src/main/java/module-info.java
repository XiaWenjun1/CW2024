/**
 * The `com.example.demo` module serves as the core module for the 1942 game project.
 * It provides the main logic, game objects, user interface, and other essential features.
 * This module relies on JavaFX for UI controls, FXML-based layouts, graphics, and media playback.
 *
 * <p>Package Structure:</p>
 * <ul>
 *   <li>{@code com.example.demo.controller}: Manages the game control logic.</li>
 *   <li>{@code com.example.demo.Level}: Handles level-related logic, including level management and views.</li>
 *   <li>{@code com.example.demo.Actor}: Defines game characters and their behaviors, such as player and enemy planes.</li>
 *   <li>{@code com.example.demo.Object}: Manages game objects and related utilities.</li>
 *   <li>{@code com.example.demo.Display}: Handles game display and rendering logic.</li>
 * </ul>
 */
module com.example.demo {
    // Required JavaFX modules for this project
    /**
     * Requires JavaFX Controls module for creating and managing UI components such as buttons, sliders, etc.
     */
    requires javafx.controls;
    /**
     * Requires JavaFX FXML module for declarative UI definition using FXML.
     */
    requires javafx.fxml;
    /**
     * Requires JavaFX Graphics module for rendering scenes and supporting graphics-related operations.
     */
    requires javafx.graphics;
    /**
     * Requires JavaFX Media module for audio and video playback.
     */
    requires javafx.media;

    // Opens packages to javafx.fxml for reflection-based FXML loading
    /**
     * Opens the {@code com.example.demo.controller} package to JavaFX FXML for loading controller classes.
     */
    opens com.example.demo.controller to javafx.fxml;
    /**
     * Opens the {@code com.example.demo.Level} package to JavaFX FXML for loading level management classes.
     */
    opens com.example.demo.Level to javafx.fxml;
    /**
     * Opens the {@code com.example.demo.Actor} package to JavaFX FXML for loading character-related classes.
     */
    opens com.example.demo.Actor to javafx.fxml;
    /**
     * Opens the {@code com.example.demo.Object} package to JavaFX FXML for loading object-related classes.
     */
    opens com.example.demo.Object to javafx.fxml;
    /**
     * Opens the {@code com.example.demo.Display} package to JavaFX FXML for loading display-related classes.
     */
    opens com.example.demo.Display to javafx.fxml;

    // Exports packages for access by other modules
    /**
     * Exports the {@code com.example.demo.Actor} package to make character-related classes accessible to other modules.
     */
    exports com.example.demo.Actor;
    /**
     * Exports the {@code com.example.demo.Actor.Boss} package for Boss-related logic.
     */
    exports com.example.demo.Actor.Boss;
    /**
     * Exports the {@code com.example.demo.Actor.EnemyPlane} package for enemy plane-related logic.
     */
    exports com.example.demo.Actor.EnemyPlane;
    /**
     * Exports the {@code com.example.demo.Actor.UserPlane} package for user-controlled plane logic.
     */
    exports com.example.demo.Actor.UserPlane;
    /**
     * Exports the {@code com.example.demo.controller} package for game control-related logic.
     */
    exports com.example.demo.controller;
    /**
     * Exports the {@code com.example.demo.Display} package for game display management.
     */
    exports com.example.demo.Display;
    /**
     * Exports the {@code com.example.demo.Level} package for level-related logic.
     */
    exports com.example.demo.Level;
    /**
     * Exports the {@code com.example.demo.Level.LevelManager} subpackage for level management logic.
     */
    exports com.example.demo.Level.LevelManager;
    /**
     * Exports the {@code com.example.demo.Level.LevelView} subpackage for level rendering logic.
     */
    exports com.example.demo.Level.LevelView;
    /**
     * Exports the {@code com.example.demo.Object} package for game object management.
     */
    exports com.example.demo.Object;
    /**
     * Exports the {@code com.example.demo.Object.Object} subpackage for additional object-related utilities.
     */
    exports com.example.demo.Object.Object;

    // Additional specific package openings for reflection
    /**
     * Opens the {@code com.example.demo.Level.LevelManager} package to JavaFX FXML for level manager-related classes.
     */
    opens com.example.demo.Level.LevelManager to javafx.fxml;
    /**
     * Opens the {@code com.example.demo.Object.Object} subpackage to JavaFX FXML for object utilities.
     */
    opens com.example.demo.Object.Object to javafx.fxml;
    /**
     * Opens the {@code com.example.demo.Actor.Boss} package to JavaFX FXML for boss character-related classes.
     */
    opens com.example.demo.Actor.Boss to javafx.fxml;
    /**
     * Opens the {@code com.example.demo.Actor.EnemyPlane} package to JavaFX FXML for enemy plane-related classes.
     */
    opens com.example.demo.Actor.EnemyPlane to javafx.fxml;
    /**
     * Opens the {@code com.example.demo.Actor.UserPlane} package to JavaFX FXML for user-controlled plane classes.
     */
    opens com.example.demo.Actor.UserPlane to javafx.fxml;
    /**
     * Opens the {@code com.example.demo.Level.LevelView} subpackage to JavaFX FXML for level view-related classes.
     */
    opens com.example.demo.Level.LevelView to javafx.fxml;
    /**
     * Opens the {@code com.example.demo.Actor.Boss.ParentBoss} subpackage to JavaFX FXML for base boss logic.
     */
    opens com.example.demo.Actor.Boss.ParentBoss to javafx.fxml;
    /**
     * Opens the {@code com.example.demo.Actor.Boss.MutationBoss1} subpackage to JavaFX FXML for MutationBoss1 logic.
     */
    opens com.example.demo.Actor.Boss.MutationBoss1 to javafx.fxml;
    /**
     * Opens the {@code com.example.demo.Actor.Boss.MutationBoss2} subpackage to JavaFX FXML for MutationBoss2 logic.
     */
    opens com.example.demo.Actor.Boss.MutationBoss2 to javafx.fxml;
    /**
     * Opens the {@code com.example.demo.Actor.Boss.MutationBoss3} subpackage to JavaFX FXML for MutationBoss3 logic.
     */
    opens com.example.demo.Actor.Boss.MutationBoss3 to javafx.fxml;
}