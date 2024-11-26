/**
 * com.example.demo module, the core module that provides the main logic and user interface for the game.
 * This module depends on JavaFX for controls, FXML, and graphics-related functionality.
 *
 * Package structure:
 * - com.example.demo.controller: Handles game control logic
 * - com.example.demo.Level: Manages level-related logic
 * - com.example.demo.Actor: Handles game characters and behaviors
 * - com.example.demo.Object: Handles game objects and related logic
 */
module com.example.demo {
    // Required JavaFX modules for the project
    requires javafx.controls; // JavaFX Controls for UI components like buttons, sliders, etc.
    requires javafx.fxml; // JavaFX FXML for declarative UI definition
    requires javafx.graphics; // JavaFX Graphics for rendering scenes
    requires javafx.media; // JavaFX Media for handling audio and video playback

    // Open the following packages to javafx.fxml for FXML reflection-based loading
    opens com.example.demo.controller to javafx.fxml; // Allows javafx.fxml to access controllers in the 'controller' package
    opens com.example.demo.Level to javafx.fxml; // Allows javafx.fxml to access classes in the 'Level' package
    opens com.example.demo.Actor to javafx.fxml; // Allows javafx.fxml to access classes in the 'Actor' package
    opens com.example.demo.Object to javafx.fxml; // Allows javafx.fxml to access classes in the 'Object' package
    opens com.example.demo.Display to javafx.fxml; // Allows javafx.fxml to access classes in the 'Display' package

    // Export the following packages to make them accessible to other modules
    exports com.example.demo.controller; // Exposes 'controller' package to other modules
    exports com.example.demo.Level; // Exposes 'Level' package to other modules
    exports com.example.demo.Level.LevelManager; // Exposes 'LevelManager' subpackage to other modules

    // Open additional specific packages to javafx.fxml for reflection-based loading
    opens com.example.demo.Level.LevelManager to javafx.fxml; // Allows javafx.fxml to access 'LevelManager' package
    opens com.example.demo.Object.Object to javafx.fxml; // Allows javafx.fxml to access 'Object' subpackage
    opens com.example.demo.Actor.Boss to javafx.fxml; // Allows javafx.fxml to access classes in 'Boss' subpackage
    opens com.example.demo.Actor.EnemyPlane to javafx.fxml; // Allows javafx.fxml to access 'EnemyPlane' subpackage
    opens com.example.demo.Actor.UserPlane to javafx.fxml; // Allows javafx.fxml to access 'UserPlane' subpackage
    opens com.example.demo.Level.LevelView to javafx.fxml; // Allows javafx.fxml to access 'LevelView' subpackage
    opens com.example.demo.Actor.Boss.ParentBoss to javafx.fxml; // Allows javafx.fxml to access 'ParentBoss' subpackage
    opens com.example.demo.Actor.Boss.MutationBoss1 to javafx.fxml; // Allows javafx.fxml to access 'MutationBoss1' subpackage
    opens com.example.demo.Actor.Boss.MutationBoss2 to javafx.fxml; // Allows javafx.fxml to access 'MutationBoss2' subpackage
    opens com.example.demo.Actor.Boss.MutationBoss3 to javafx.fxml; // Allows javafx.fxml to access 'MutationBoss3' subpackage
}