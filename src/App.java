import ui.*;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.Scene;

public class App extends Application {
    public void start(Stage mainStage) throws Exception {
        MainPanel mainPanel = new MainPanel();
        Scene mainScene = new Scene(mainPanel, 1920, 1080);
        LoginPanel loginPanel = new LoginPanel(mainStage, mainScene);
        Scene loginScene = new Scene(loginPanel, 1920, 1080);

        mainStage.setTitle("FTP Client");
        mainStage.setScene(loginScene);
        mainStage.setMaximized(true);
        mainStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
