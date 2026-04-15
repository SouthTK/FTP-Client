import ui.LoginPanel;
import ui.MainPanel;
import background.ProcessThread;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.Scene;

public class App extends Application {
    public void start(Stage mainStage) throws Exception {
        MainPanel mainPanel = new MainPanel();
        Scene mainScene = new Scene(mainPanel);
        LoginPanel loginPanel = new LoginPanel(mainStage, mainScene);
        Scene loginScene = new Scene(loginPanel);

        mainStage.setTitle("FTP Client");
        mainStage.setScene(loginScene);
        mainStage.setMaximized(true);
        mainStage.show();

        ProcessThread process = new ProcessThread(mainPanel);
        Thread processThread = new Thread(process);
        mainPanel.linkProcess(process);
        loginPanel.linkProcess(process);
        processThread.start();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
