package ui;

import javafx.stage.Stage;
import javafx.scene.Scene;

import background.ProcessThread;

public class SceneManager {
    private Stage mainStage;
    private Scene mainScene;
    private Scene loginScene;
    private ProcessThread process;

    public SceneManager(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void linkProcess(ProcessThread input) {
        this.process = input;
    }

    public void setUpSceneManager(Scene main, Scene login) {
        this.mainScene = main;
        this.loginScene = login;
    }

    public void setToMainScene(String user, String pass) {
        this.process.setUser(user, pass);
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