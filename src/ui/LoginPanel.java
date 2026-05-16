package ui;

import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.Scene;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.stage.Stage;

import background.ProcessThread;

public class LoginPanel extends BorderPane {
    private SceneManager manager;

    public LoginPanel(SceneManager manager) {     
        this.manager = manager;
        this.setUpCenter();
        this.setUpTop();
    }

    private void setUpCenter() {
        Label loginLabel = new Label("Login into the app");
        loginLabel.setFont(new Font("Arial Black", 24));
        loginLabel.setTextFill(Color.BLACK); 
        Label nextLabel = new Label("Or continue without login");
        nextLabel.setFont(new Font("Arial Black", 18));
        nextLabel.setTextFill(Color.BLACK); 

        TextField usernameBox = new TextField();
        usernameBox.setPromptText("Username"); 
        PasswordField passwordBox = new PasswordField();
        passwordBox.setPromptText("Password"); 

        Button loginButton = new Button();
        loginButton.setText("Log in");
        loginButton.setPrefWidth(300);
        Button nextButton = new Button();
        nextButton.setText("Use without login");
        nextButton.setPrefWidth(300);

        nextButton.setOnAction(event -> {
            String user = "anonymous";
            this.manager.setToMainScene(user, null);
        });

        loginButton.setOnAction(event -> {
            String user = usernameBox.getText();
            String pass = passwordBox.getText();
            this.manager.setToMainScene(user, pass);
        });

        VBox centerLayout = new VBox(10);
        centerLayout.setAlignment(Pos.CENTER); 
        centerLayout.getChildren().addAll(loginLabel, usernameBox, passwordBox, loginButton, nextLabel, nextButton); 
        centerLayout.setPadding(new Insets(0, 20, 20, 20)); 
        centerLayout.setMaxWidth(300);
        centerLayout.setMaxHeight(300);

        this.setCenter(centerLayout);
    }
    
    private void setUpTop() {
        VBox topLayout = new VBox(10);
        topLayout.setAlignment(Pos.CENTER); 
        topLayout.setPadding(new Insets(0, 20, 20, 20)); 
        topLayout.setMinHeight(70);
        topLayout.setMaxHeight(70);

        this.setTop(topLayout);
    }

}
