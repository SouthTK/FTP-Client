package ui;

import javafx.stage.Stage;
import javafx.scene.Scene;

public class SceneManager {
    private Stage mainStage;
    private Scene mainScene;
    private Scene loginScene;

    public SceneManager(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setUpSceneManager(Scene main, Scene login) {
        this.mainScene = main;
        this.loginScene = login;
    }

    public void setToMainScene() {
        this.mainStage.setScene(this.mainScene);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
    }

    public void setToLoginScene() {
        this.mainStage.setScene(this.loginScene);
        mainStage.setMaximized(false); 
        mainStage.setMaximized(true);
    }
}