import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("FTP Client");
        //mainStage.setScene(new Scene(root));
        mainStage.setMaximized(true);
        mainStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
