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

import javafx.application.Platform;

import background.ProcessThread;

public class MainPanel extends BorderPane {
    private SceneManager manager;
    private ProcessThread process;

    public MainPanel(SceneManager manager) {  
        this.manager = manager; 
        this.setUpTop(); 
        this.setUpLeft();
        this.setUpCenter(); 
        this.setUpBottom();
        this.setStyle("-fx-background-color: rgba(20, 20, 20, 1);");
    }

    public void linkProcess(ProcessThread input) {
        this.process = input;
    }

    public void updateOutput(String input) {
        Platform.runLater(() -> {
            VBox box = (VBox) this.getBottom();
            TextArea outputBox = (TextArea) box.getChildren().get(0);
            outputBox.appendText(input + System.lineSeparator());
        });    
    }

    public void updateCurrentDirectory(String input) {
        Platform.runLater(() -> {
            VBox box = (VBox) this.getLeft();
            Component.CustomButton currentDirectory = (Component.CustomButton) box.getChildren().get(0);
            currentDirectory.setText(input);
        });
    }

    public void clearDirectory() {
        Platform.runLater(() -> {
            VBox box = (VBox) this.getLeft();
            TextArea directoryBox = (TextArea) box.getChildren().get(1);
            directoryBox.clear();
        });
    }
    
    public void updateDirectory(String input) {
        Platform.runLater(() -> {
            VBox box = (VBox) this.getLeft();
            TextArea directoryBox = (TextArea) box.getChildren().get(1);
            directoryBox.appendText(input + System.lineSeparator());
        });
    }

    public void clearDirectoryCombo() {
        Platform.runLater(() -> {
            VBox box = (VBox) this.getCenter();
            Component.CustomDropDown directoryCombo = (Component.CustomDropDown) box.getChildren().get(3);
            directoryCombo.getItems().clear();
            directoryCombo.getItems().add("..");
        });
    }

    public void updateDirectoryCombo(String input) {
        Platform.runLater(() -> {
            VBox box = (VBox) this.getCenter();
            Component.CustomDropDown directoryCombo = (Component.CustomDropDown) box.getChildren().get(3);
            directoryCombo.getItems().add(input);
        });
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
            this.process.disconnect();
            this.manager.setToLoginScene();
            this.clearDirectory();
            this.clearDirectoryCombo();
            
            VBox box = (VBox) this.getBottom();
            TextArea outputBox = (TextArea) box.getChildren().get(0);
            outputBox.clear();
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

    private void setUpLeft() {
        Component.CustomButton currentDirectory = new Component.CustomButton();
        currentDirectory.setText("");
        currentDirectory.setPrefWidth(200);

        TextArea directoryBox = new TextArea();
        directoryBox.setStyle("-fx-control-inner-background: rgba(30, 30, 30, 1);");
        directoryBox.setEditable(false); 
        directoryBox.setPromptText("Current directory listing will appear here...");
        directoryBox.setPrefHeight(600);

        VBox leftLayout = new VBox(10);
        leftLayout.getChildren().addAll(currentDirectory, directoryBox); 
        leftLayout.setPadding(new Insets(10, 10, 10, 10)); 

        this.setLeft(leftLayout);
    }

    private void setUpCenter() {
        int preWidth = 120;
        Component.CustomButton refreshButton = new Component.CustomButton();
        refreshButton.setText("Refresh");
        refreshButton.setPrefWidth(preWidth);
        refreshButton.setOnAction(event -> {
            this.process.refresh();
        });

        Component.CustomButton createFolderButton = new Component.CustomButton();
        createFolderButton.setText("Create Folder");
        createFolderButton.setPrefWidth(preWidth);
        createFolderButton.setOnAction(event -> {
            this.process.test();
        });

        Component.CustomButton deleteButton = new Component.CustomButton();
        deleteButton.setText("Delete");
        deleteButton.setPrefWidth(preWidth);
        deleteButton.setOnAction(event -> {
            this.process.test();
        });

        Component.CustomDropDown directoryCombo = new Component.CustomDropDown();
        directoryCombo.setPrefWidth(preWidth);

        Component.CustomButton directoryButton = new Component.CustomButton();
        directoryButton.setText("Change directory");
        directoryButton.setPrefWidth(preWidth);
        directoryButton.setOnAction(event -> {
            String directory = directoryCombo.getValue(); 
            this.process.changeDirectory(directory);
        });

        Component.CustomButton uploadButton = new Component.CustomButton();
        uploadButton.setText("Upload");
        uploadButton.setPrefWidth(preWidth);
        uploadButton.setOnAction(event -> {
            this.process.test();
        });

        Component.CustomButton downloadButton = new Component.CustomButton();
        downloadButton.setText("Download");
        downloadButton.setPrefWidth(preWidth);
        downloadButton.setOnAction(event -> {
            this.process.test();
        });

        VBox centerLayout = new VBox(10);
        centerLayout.getChildren().addAll(refreshButton, createFolderButton, 
                deleteButton, directoryCombo, directoryButton, uploadButton, downloadButton); 
        centerLayout.setPadding(new Insets(10, 10, 10, 10)); 

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