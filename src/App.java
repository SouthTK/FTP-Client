import ui.Component;
import ui.LoginPanel;
import ui.MainPanel;
import ui.SceneManager;

import background.ProcessThread;
import background.ListThread;
import background.RetvThread;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.Scene;

public class App extends Application {
    public void start(Stage mainStage) throws Exception {
        SceneManager manager = new SceneManager(mainStage);
        MainPanel mainPanel = new MainPanel(manager);
        Scene mainScene = new Scene(mainPanel);
        LoginPanel loginPanel = new LoginPanel(manager);
        Scene loginScene = new Scene(loginPanel);
        manager.setUpSceneManager(mainScene, loginScene);

        mainStage.setTitle("FTP Client");
        mainStage.setScene(loginScene);
        mainStage.setMaximized(true);
        mainStage.show();

        ProcessThread process = new ProcessThread(mainPanel);
        Thread processThread = new Thread(process);
        mainPanel.linkProcess(process);
        manager.linkProcess(process);
        processThread.start();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
