/**
 * The `com.example.demo` module serves as the core module for the 1942 game project.
 * It provides the main game logic, game objects, user interface, and other essential features.
 * The module leverages JavaFX for creating the UI, handling FXML-based layouts, rendering graphics,
 * and supporting media playback.
 *
 * <p>Package Overview:</p>
 * <ul>
 *   <li>{@code com.example.demo.Actor}: Defines game characters (actors) and their behaviors, including player, enemy planes, boss and projectiles etc.</li>
 *   <li>{@code com.example.demo.Actor.Object}: Manages game objects, such as AmmoBox and Heart.</li>
 *   <li>{@code com.example.demo.Actor.Plane}: Manages game planes, such as user plane and enemy planes.</li>
 *   <li>{@code com.example.demo.Actor.Projectile}: Manages game projectiles, such as user projectile and enemy projectile.</li>
 *   <li>{@code com.example.demo.Controller}: Game program entrance.</li>
 *   <li>{@code com.example.demo.Display}: Handles game display logic, including scene management and rendering of game visuals.</li>
 *   <li>{@code com.example.demo.Level}: Handles level management, including level logic, enemy spawning, and level progression.</li>
 *   <li>{@code com.example.demo.Level.LevelManager}: Controls the flow and progression of levels within the game.</li>
 *   <li>{@code com.example.demo.Level.LevelView}: Responsible for rendering the view for each level, including background and enemies.</li>
 *   <li>{@code com.example.demo.Ui}: Manages the user interface components, including menus, settings, and HUD elements.</li>
 * </ul>
 */
module com.example.demo {

    // Required JavaFX modules for this project
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    // Opens specific packages for reflection-based FXML loading
    opens com.example.demo.Actor to javafx.fxml;
    opens com.example.demo.Actor.Object to javafx.fxml;
    opens com.example.demo.Actor.Plane to javafx.fxml;
    opens com.example.demo.Actor.Plane.Boss to javafx.fxml;
    opens com.example.demo.Actor.Projectile to javafx.fxml;
    opens com.example.demo.Controller to javafx.fxml;
    opens com.example.demo.Display to javafx.fxml;
    opens com.example.demo.Level to javafx.fxml;
    opens com.example.demo.Level.LevelManager to javafx.fxml;
    opens com.example.demo.Level.LevelView to javafx.fxml;
    opens com.example.demo.Ui to javafx.fxml;

    // Exports packages for access by other modules
    exports com.example.demo.Actor;
    exports com.example.demo.Actor.Object;
    exports com.example.demo.Actor.Plane;
    exports com.example.demo.Actor.Plane.Boss;
    exports com.example.demo.Actor.Projectile;
    exports com.example.demo.Controller;
    exports com.example.demo.Display;
    exports com.example.demo.Level;
    exports com.example.demo.Level.LevelManager;
    exports com.example.demo.Level.LevelView;
    exports com.example.demo.Ui;
}