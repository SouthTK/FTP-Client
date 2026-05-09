package ui;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

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


    public void updateCurrentDirectory(String input) {
        Platform.runLater(() -> {
            VBox box = (VBox) this.getLeft();
            Component.CustomButton currentDirectory = (Component.CustomButton) box.getChildren().get(0);
            currentDirectory.setText(input);
        });
    }

    public void updateOutput(String input) {
        Platform.runLater(() -> {
            VBox box = (VBox) this.getBottom();
            TextArea outputBox = (TextArea) box.getChildren().get(0);
            outputBox.appendText(input + System.lineSeparator());
        });    
    }

    public void clearOutput() {
        Platform.runLater(() -> {
            VBox box = (VBox) this.getBottom();
            TextArea outputBox = (TextArea) box.getChildren().get(0);
            outputBox.clear();
        });   
    }

    
    public void updateDirectory(String input) {
        Platform.runLater(() -> {
            String[] parts = input.trim().split("\\s+");
            String date = parts[5] + " " + parts[6] + " " + parts[7];
            String name = parts[8];
            if (parts.length > 9) {
                for (int i = 9; i < parts.length; i++) {name += " " + parts[i];}
            }

            Component.FileInfo info = new Component.FileInfo(parts[0], parts[1], parts[2], parts[3], 
                    parts[4], date, name);

            VBox box = (VBox) this.getLeft();
            Component.CustomView directoryView = (Component.CustomView) box.getChildren().get(1);
            directoryView.getItems().add(info);
        });
    }

    public void clearDirectory(boolean fullClear) {
        VBox box = (VBox) this.getLeft();
        Component.CustomView directoryView = (Component.CustomView) box.getChildren().get(1);
        directoryView.getItems().clear();
        if (!fullClear) {
            Component.FileInfo parent = new Component.FileInfo(" ", "", "", "", "", "", "..");
            Component.FileInfo root = new Component.FileInfo(" ", "", "", "", "", "", "/");
            directoryView.getItems().add(root);
            directoryView.getItems().add(parent);
        }
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
            this.updateCurrentDirectory("");
            this.clearDirectory(true);
        });

        Component.CustomButton logoutButton = new Component.CustomButton();
        logoutButton.setText("Log out");
        logoutButton.setPrefWidth(80);
        logoutButton.setOnAction(event -> {
            this.process.disconnect();
            this.manager.setToLoginScene();
            this.updateCurrentDirectory("");
            this.clearDirectory(true);  
            this.clearOutput();
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

        Component.CustomView directoryView = new Component.CustomView();

        VBox leftLayout = new VBox(10);
        leftLayout.getChildren().addAll(currentDirectory, directoryView); 
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

        Component.CustomButton deleteButton = new Component.CustomButton();
        deleteButton.setText("Delete");
        deleteButton.setPrefWidth(preWidth);
        deleteButton.setOnAction(event -> {
            VBox box = (VBox) this.getLeft();
            TableView<Component.FileInfo> directoryView = (TableView<Component.FileInfo>) box.getChildren().get(1);
            Component.FileInfo selectedFile = directoryView.getSelectionModel().getSelectedItem();

            if (selectedFile != null) {
                String fileName = selectedFile.getName();
                this.process.deleteFile(fileName);
            }
        });

        Component.CustomButton directoryButton = new Component.CustomButton();
        directoryButton.setText("Change directory");
        directoryButton.setPrefWidth(preWidth);
        directoryButton.setOnAction(event -> {
            VBox box = (VBox) this.getLeft();
            TableView<Component.FileInfo> directoryView = (TableView<Component.FileInfo>) box.getChildren().get(1);
            Component.FileInfo selectedFile = directoryView.getSelectionModel().getSelectedItem();

            if (selectedFile != null) {
                String directory = selectedFile.getName();
                this.process.changeDirectory(directory);
            }
        });

        TextField nameBox = new TextField();
        nameBox.setPromptText("Folder Name"); 
        nameBox.setMinWidth(preWidth);
        nameBox.setMaxWidth(preWidth);
        nameBox.setStyle("-fx-control-inner-background: rgba(30, 30, 30, 1);"
                + "-fx-prompt-text-fill: white;");

        Component.CustomButton mkdirButton = new Component.CustomButton();
        mkdirButton.setText("Create Folder");
        mkdirButton.setPrefWidth(preWidth);
        mkdirButton.setOnAction(event -> {
            this.process.createFolder(nameBox.getText());
        });

        Component.CustomButton rmdirButton = new Component.CustomButton();
        rmdirButton.setText("Delete Folder");
        rmdirButton.setPrefWidth(preWidth);
        rmdirButton.setOnAction(event -> {
            VBox box = (VBox) this.getLeft();
            TableView<Component.FileInfo> directoryView = (TableView<Component.FileInfo>) box.getChildren().get(1);
            Component.FileInfo selectedFile = directoryView.getSelectionModel().getSelectedItem();

            if (selectedFile != null) {
                String directory = selectedFile.getName();
                this.process.deleteFolder(directory);
            }  
        });

        Component.CustomButton uploadButton = new Component.CustomButton();
        uploadButton.setText("Upload");
        uploadButton.setPrefWidth(preWidth);
        uploadButton.setOnAction(event -> {
            //this.process.test();
        });

        Component.CustomButton downloadButton = new Component.CustomButton();
        downloadButton.setText("Download");
        downloadButton.setPrefWidth(preWidth);
        downloadButton.setOnAction(event -> {
            //this.process.test();
        });

        VBox centerLayout = new VBox(10);
        centerLayout.getChildren().addAll(refreshButton, deleteButton, directoryButton, nameBox,
                mkdirButton, rmdirButton, uploadButton, downloadButton); 
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