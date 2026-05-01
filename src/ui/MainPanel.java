package ui;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import background.ProcessThread;

public class MainPanel extends BorderPane {
    private SceneManager manager;
    private ProcessThread process;

    public MainPanel(SceneManager manager) {  
        this.manager = manager; 
        this.setUpTop(); 
        this.setUpCenter(); 
        this.setUpBottom();
        this.setStyle("-fx-background-color: rgba(20, 20, 20, 1);");
    }

    public void linkProcess(ProcessThread input) {
        this.process = input;
    }

    public void updateOutput(String input) {
        VBox box = (VBox) this.getBottom();
        TextArea outputBox = (TextArea) box.getChildren().get(0);
        outputBox.appendText(input + System.lineSeparator());
    }

    private void setUpTop() {
        TextField addressBox = new TextField();
        addressBox.setPromptText("Address"); 
        addressBox.setStyle("-fx-control-inner-background: rgba(30, 30, 30, 1);"
                + "-fx-prompt-text-fill: white;");

        Component.CustomButton connectButton = new Component.CustomButton();
        connectButton.setText("Connect");
        connectButton.setPrefWidth(80);
        connectButton.setOnAction(event -> {
            this.process.connect(addressBox.getText());
        });

        Component.CustomButton disconnectButton = new Component.CustomButton();
        disconnectButton.setText("Disconnect");
        disconnectButton.setPrefWidth(80);
        disconnectButton.setOnAction(event -> {
            this.process.disconnect();
        });

        Component.CustomButton logoutButton = new Component.CustomButton();
        logoutButton.setText("Log out");
        logoutButton.setPrefWidth(80);
        logoutButton.setOnAction(event -> {
            VBox box = (VBox) this.getBottom();
            TextArea outputBox = (TextArea) box.getChildren().get(0);
            outputBox.clear();
            this.manager.setToLoginScene();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topLayout = new HBox(10);
        topLayout.getChildren().addAll(addressBox, connectButton, disconnectButton, spacer, logoutButton); 
        topLayout.setAlignment(Pos.CENTER_LEFT); 
        topLayout.setPadding(new Insets(0, 20, 20, 20)); 
        topLayout.setMinHeight(70);
        topLayout.setMaxHeight(70);
        topLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 1);"
                + "-fx-background-image: url('image/logo.png');"
                + "-fx-background-repeat: no-repeat; "
                + "-fx-background-position: center top; " 
                + "-fx-background-size: 400 70;");
                
        this.setTop(topLayout);
    }

    private void setUpCenter() {
        Component.CustomButton refreshButton = new Component.CustomButton();
        refreshButton.setText("Refresh");
        refreshButton.setPrefWidth(80);
        refreshButton.setOnAction(event -> {
            this.process.pwd();
        });

        VBox centerLayout = new VBox(10);
        centerLayout.getChildren().addAll(refreshButton); 
        centerLayout.setPadding(new Insets(0, 10, 10, 10)); 

        this.setCenter(centerLayout);
    }

    private void setUpBottom() {
        TextArea outputBox = new TextArea();
        outputBox.setStyle("-fx-control-inner-background: rgba(30, 30, 30, 1);");
        outputBox.setEditable(false); 
        outputBox.setPromptText("Output will appear here...");

        VBox bottomLayout = new VBox(10);
        bottomLayout.getChildren().addAll(outputBox); 
        bottomLayout.setPadding(new Insets(0, 10, 10, 10)); 

        this.setBottom(bottomLayout);
    }
}