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
import javafx.scene.image.Image;
import javafx.scene.Scene;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.stage.Stage;

import javafx.event.ActionEvent;

import javafx.beans.binding.Bindings;

public class LoginPanel extends BorderPane {
    private Stage mainStage;
    private Scene mainScene;

    public LoginPanel(Stage inputStage, Scene inputScene) {     
        this.mainStage = inputStage;
        this.mainScene = inputScene;
        this.setUpCenter(this);
        this.setStyle("-fx-background-image: url('image/pacific.jpg'); " +
                "-fx-background-size: cover; " + 
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-position: center;");
    }
    // private void moveToMainScene() {
    //     this.mainStage.setScene(mainScene);
    // }

    private void setUpCenter(BorderPane mainPane) {
        Label loginLabel = new Label("Login into the app");
        loginLabel.setFont(new Font("Arial Black", 24));
        loginLabel.setTextFill(Color.WHITE); 
        Label nextLabel = new Label("Or continue without login");
        nextLabel.setFont(new Font("Arial Black", 18));
        nextLabel.setTextFill(Color.WHITE); 

        TextField usernameBox = new TextField();
        usernameBox.setPromptText("Username"); 
        usernameBox.setStyle("-fx-control-inner-background: rgba(30, 30, 30, 1); -fx-prompt-text-fill: white; -fx-pref-height: 40px;");
        PasswordField passwordBox = new PasswordField();
        passwordBox.setPromptText("Password"); 
        passwordBox.setStyle("-fx-control-inner-background: rgba(30, 30, 30, 1); -fx-prompt-text-fill: white; -fx-pref-height: 40px;");

        Component.CustomButton loginButton = new Component.CustomButton();
        loginButton.setText("Log in");
        loginButton.setPrefWidth(300);
        Component.CustomButton nextButton = new Component.CustomButton();
        nextButton.setText("Use without login");
        nextButton.setPrefWidth(300);

        nextButton.setOnAction(event -> {
            System.out.println("Button is cicked!");
            this.mainStage.setScene(mainScene);
            //hmmm
        });

        VBox centerLayout = new VBox(10);
        centerLayout.setAlignment(Pos.CENTER); 
        centerLayout.getChildren().addAll(loginLabel, usernameBox, passwordBox, loginButton, nextLabel, nextButton); 
        centerLayout.setPadding(new Insets(0, 20, 20, 20)); 
        centerLayout.setMaxWidth(300);
        centerLayout.setMaxHeight(300);
        centerLayout.setStyle("-fx-background-color: rgba(20, 20, 20, 1); -fx-background-radius: 15;");

        mainPane.setCenter(centerLayout);
    }
}
