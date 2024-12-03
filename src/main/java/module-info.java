/**
 * The `com.example.demo` module serves as the core module for the 1942 game project.
 * It provides the main game logic, game objects, user interface, and other essential features.
 * The module leverages JavaFX for creating the UI, handling FXML-based layouts, rendering graphics,
 * and supporting media playback.
 *
 * <p>Package Overview:</p>
 * <ul>
 *   <li>{@code com.example.demo.Controller}: Manages the game control logic.</li>
 *   <li>{@code com.example.demo.Level}: Handles level management, including level logic and views.</li>
 *   <li>{@code com.example.demo.Actor}: Defines game characters and their behaviors, including player and enemy planes.</li>
 *   <li>{@code com.example.demo.Object}: Manages game objects and related utilities.</li>
 *   <li>{@code com.example.demo.Display}: Handles game display and rendering logic.</li>
 * </ul>
 */
module com.example.demo {

    // Required JavaFX modules for this project
    /**
     * Requires the JavaFX Controls module, which provides essential UI components like buttons, sliders, etc.
     */
    requires javafx.controls;

    /**
     * Requires the JavaFX FXML module for declarative UI definition using FXML, enabling easy scene building.
     */
    requires javafx.fxml;

    /**
     * Requires the JavaFX Graphics module, which facilitates rendering scenes and handling graphics-related operations.
     */
    requires javafx.graphics;

    /**
     * Requires the JavaFX Media module, enabling audio and video playback for game sound effects and background music.
     */
    requires javafx.media;

    // Opens specific packages for reflection-based FXML loading

    /**
     * Opens the {@code com.example.demo.Actor} package to JavaFX FXML for dynamic loading of character-related classes.
     */
    opens com.example.demo.Actor to javafx.fxml;

    /**
     * Opens the {@code com.example.demo.Object} package to JavaFX FXML for dynamic loading of object-related classes.
     */
    opens com.example.demo.Actor.Object to javafx.fxml;

    /**
     * Opens the {@code com.example.demo.Actor.Plane} package to JavaFX FXML for dynamic loading of plane-related classes.
     */
    opens com.example.demo.Actor.Plane to javafx.fxml;

    /**
     * Opens the {@code com.example.demo.Actor.Plane.Boss} package to JavaFX FXML for dynamic loading of boss-related classes.
     */
    opens com.example.demo.Actor.Plane.Boss to javafx.fxml;

    /**
     * Opens the {@code com.example.demo.Actor.Projectile} package to JavaFX FXML for dynamic loading of projectile-related classes.
     */
    opens com.example.demo.Actor.Projectile to javafx.fxml;

    /**
     * Opens the {@code com.example.demo.Controller} package to JavaFX FXML for dynamic loading of controller classes.
     */
    opens com.example.demo.Controller to javafx.fxml;

    /**
     * Opens the {@code com.example.demo.Display} package to JavaFX FXML for dynamic loading of display-related classes.
     */
    opens com.example.demo.Display to javafx.fxml;

    /**
     * Opens the {@code com.example.demo.Level} package to JavaFX FXML for dynamic loading of level management classes.
     */
    opens com.example.demo.Level to javafx.fxml;

    /**
     * Opens the {@code com.example.demo.Level.LevelManager} package to JavaFX FXML for dynamic loading of level manager classes.
     */
    opens com.example.demo.Level.LevelManager to javafx.fxml;

    /**
     * Opens the {@code com.example.demo.Level.LevelView} subpackage to JavaFX FXML for dynamic loading of level view classes.
     */
    opens com.example.demo.Level.LevelView to javafx.fxml;

    /**
     * Opens the {@code com.example.demo.Ui} package to JavaFX FXML for dynamic loading of UI-related classes.
     */
    opens com.example.demo.Ui to javafx.fxml;

    // Exports packages for access by other modules

    /**
     * Exports the {@code com.example.demo.Actor} package to make character-related classes accessible to other modules.
     */
    exports com.example.demo.Actor;

    /**
     * Exports the {@code com.example.demo.Actor.Object} package to handle game object management.
     */
    exports com.example.demo.Actor.Object;

    /**
     * Exports the {@code com.example.demo.Actor.Plane} package, which contains plane-related classes for both player and enemy planes.
     */
    exports com.example.demo.Actor.Plane;

    /**
     * Exports the {@code com.example.demo.Actor.Plane.Boss} package, which handles boss plane-related logic.
     */
    exports com.example.demo.Actor.Plane.Boss;

    /**
     * Exports the {@code com.example.demo.Actor.Projectile} package to handle projectile-related game logic.
     */
    exports com.example.demo.Actor.Projectile;

    /**
     * Exports the {@code com.example.demo.Controller} package for game control logic.
     */
    exports com.example.demo.Controller;

    /**
     * Exports the {@code com.example.demo.Display} package for game display management.
     */
    exports com.example.demo.Display;

    /**
     * Exports the {@code com.example.demo.Level} package for level-related logic and functionalities.
     */
    exports com.example.demo.Level;

    /**
     * Exports the {@code com.example.demo.Level.LevelManager} subpackage for level management and control logic.
     */
    exports com.example.demo.Level.LevelManager;

    /**
     * Exports the {@code com.example.demo.Level.LevelView} subpackage for level rendering and view management.
     */
    exports com.example.demo.Level.LevelView;

    /**
     * Exports the {@code com.example.demo.Ui} package for handling user interface-related logic.
     */
    exports com.example.demo.Ui;
}