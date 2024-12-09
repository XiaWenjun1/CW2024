package com.example.demo.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

public class TestMain extends ApplicationTest {

    private Stage testStage;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Main/Main.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 1300, 750);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sky Battle");

        this.testStage = stage;
        stage.show();
    }

    @Test
    void testSceneSize() {
        assertNotNull(testStage.getScene(), "Scene should not be null.");
        assertEquals(1300, testStage.getScene().getWidth(), "Scene width should be 1300.");
        assertEquals(750, testStage.getScene().getHeight(), 0.5, "Scene height should be approximately 750.");
    }

    @Test
    void testWindowProperties() {
        assertFalse(testStage.isResizable(), "Window should not be resizable.");
        assertEquals("Sky Battle", testStage.getTitle(), "Window title should be 'Sky Battle'.");
    }

    @Test
    void testFXMLLoading() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/layout/Main/Main.fxml"));
        assertDoesNotThrow(() -> fxmlLoader.load(), "FXML file should load without exceptions.");
    }
}