package ui;

import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;

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

import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors;

import java.io.File;

import background.ProcessThread;

public class MainPanel extends BorderPane {
    private SceneManager manager;
    private ProcessThread process;
    private ExecutorService pool;

    private Button currentDirectory;
    private Component.CustomView directoryView;
    private TextArea outputBox;
    private TextArea systemBox;

    public MainPanel(SceneManager manager) {  
        this.manager = manager; 
        this.pool = Executors.newFixedThreadPool(1);
        this.setUpTop(); 
        this.setUpLeft();
        this.setUpRight(); 
    }

    public void linkProcess(ProcessThread input) {
        this.process = input;
    }


    public void updateCurrentDirectory(String input) {
        Platform.runLater(() -> {
            this.currentDirectory.setText(input);
        });
    }

    public void updateOutput(String input) {
        Platform.runLater(() -> {
            this.outputBox.appendText(input + System.lineSeparator());
        });    
    }

    public void clearOutput() {
        Platform.runLater(() -> {
            this.outputBox.clear();
        });   
    }

    public void updateSystem(String input) {
        Platform.runLater(() -> {
            this.systemBox.appendText(input + System.lineSeparator());
        });    
    }

    public void clearSystem() {
        Platform.runLater(() -> {
            this.systemBox.clear();
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

            Component.FileInfo info = new Component.FileInfo(parts[0], 
                    parts[1], parts[2], parts[3], parts[4], date, name);
            this.directoryView.getItems().add(info);
        });
    }

    public void clearDirectory(boolean fullClear) {
        this.directoryView.getItems().clear();
        if (!fullClear) {
            Component.FileInfo parent = new Component.FileInfo(" ", "", "", "", "", "", "..");
            Component.FileInfo root = new Component.FileInfo(" ", "", "", "", "", "", "/");
            this.directoryView.getItems().add(root);
            this.directoryView.getItems().add(parent);
        }
    }

    private void setUpTop() {
        TextField addressBox = new TextField();
        addressBox.setPromptText("Address"); 

        Button connectButton = new Button();
        connectButton.setText("Connect");
        connectButton.setPrefWidth(80);
        connectButton.setOnAction(event -> {
            pool.execute(() -> {
                this.process.connect(addressBox.getText());
            });
        });

        Button disconnectButton = new Button();
        disconnectButton.setText("Disconnect");
        disconnectButton.setPrefWidth(80);
        disconnectButton.setOnAction(event -> {
            pool.execute(() -> {
                this.process.disconnect();
                this.updateCurrentDirectory("");
                this.clearDirectory(true);
            });
        });

        Button logoutButton = new Button();
        logoutButton.setText("Log out");
        logoutButton.setPrefWidth(80);
        logoutButton.setOnAction(event -> {
                this.process.disconnect();
                this.manager.setToLoginScene();
                this.updateCurrentDirectory("");
                this.clearDirectory(true);  
                this.clearOutput();
                this.clearSystem();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topLayout = new HBox(10);
        topLayout.getChildren().addAll(addressBox, connectButton, disconnectButton, spacer, logoutButton); 
        topLayout.setAlignment(Pos.CENTER_LEFT); 
        topLayout.setPadding(new Insets(0, 20, 20, 20)); 
        topLayout.setMinHeight(70);
        topLayout.setMaxHeight(70);
        this.setTop(topLayout);
    }

    private void setUpLeft() {
        this.currentDirectory = new Button();
        this.currentDirectory.setText("");
        this.currentDirectory.setPrefWidth(900);

        this.directoryView = new Component.CustomView();
        this.directoryView.setPrefWidth(900); 

        VBox leftBox = new VBox(10);
        leftBox.getChildren().addAll(currentDirectory, directoryView); 
        leftBox.setPadding(new Insets(10, 10, 10, 10)); 

        this.outputBox = new TextArea();
        this.outputBox.setEditable(false); 
        this.outputBox.setPromptText("Output will appear here...");
        this.outputBox.setMaxWidth(600);
        this.outputBox.setMinWidth(600);

        this.systemBox = new TextArea();
        this.systemBox.setEditable(false); 
        this.systemBox.setPromptText("Output will appear here...");
        this.systemBox.setMaxWidth(290);
        this.systemBox.setMinWidth(290);

        HBox rightBox = new HBox(10);
        rightBox.getChildren().addAll(outputBox, systemBox); 
        rightBox.setPadding(new Insets(0, 10, 10, 10)); 

        VBox leftLayout = new VBox(0);
        leftLayout.getChildren().addAll(leftBox, rightBox);
        leftLayout.setPadding(new Insets(0, 10, 10, 10)); 
        this.setLeft(leftLayout);
    }

    private void setUpRight() {
        int preWidth = 120;

        Button refreshButton = new Button();
        refreshButton.setText("Refresh");
        refreshButton.setPrefWidth(preWidth);
        refreshButton.setOnAction(event -> {
            pool.execute(() -> {
                this.process.refresh();
            });
        });

        Button deleteButton = new Button();
        deleteButton.setText("Delete");
        deleteButton.setPrefWidth(preWidth);
        deleteButton.setOnAction(event -> {
            Component.FileInfo selectedFile = directoryView.getSelectionModel().getSelectedItem();

            if (selectedFile != null) {
                pool.execute(() -> {
                    String fileName = selectedFile.getName();
                    this.process.deleteFile(fileName);
                });
            }
        });

        Button directoryButton = new Button();
        directoryButton.setText("Change Directory");
        directoryButton.setPrefWidth(preWidth);
        directoryButton.setOnAction(event -> {
            Component.FileInfo selectedFile = directoryView.getSelectionModel().getSelectedItem();

            if (selectedFile != null) {    
                pool.execute(() -> {
                    String directory = selectedFile.getName();
                    this.process.changeDirectory(directory);
                });
            }
        });

        TextField nameBox = new TextField();
        nameBox.setPromptText("Folder Name"); 
        nameBox.setMinWidth(preWidth);
        nameBox.setMaxWidth(preWidth);

        Button mkdirButton = new Button();
        mkdirButton.setText("Create Folder");
        mkdirButton.setPrefWidth(preWidth);
        mkdirButton.setOnAction(event -> {
            pool.execute(() -> {
                this.process.createFolder(nameBox.getText());
            });
        });

        Button rmdirButton = new Button();
        rmdirButton.setText("Delete Folder");
        rmdirButton.setPrefWidth(preWidth);
        rmdirButton.setOnAction(event -> {
            Component.FileInfo selectedFile = directoryView.getSelectionModel().getSelectedItem();

            if (selectedFile != null) {
                pool.execute(() -> {
                    String directory = selectedFile.getName();
                    this.process.deleteFolder(directory);
                });
            }  
        });

        Button uploadButton = new Button();
        uploadButton.setText("Upload");
        uploadButton.setPrefWidth(preWidth * 2 + 20);
        uploadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(this.manager.getStage());

            if (selectedFile != null) {
                pool.execute(() -> {
                    String fileName = selectedFile.getName();
                    String filePath = selectedFile.getAbsolutePath();
                    this.process.upload(filePath, fileName);
                });
            }  
        });

        Button downloadButton = new Button();
        downloadButton.setText("Download");
        downloadButton.setPrefWidth(preWidth * 2 + 20);
        downloadButton.setOnAction(event -> {
            Component.FileInfo selectedFile = directoryView.getSelectionModel().getSelectedItem();
            DirectoryChooser folderChooser = new DirectoryChooser();
            File selectedFolder = folderChooser.showDialog(this.manager.getStage());

            if (selectedFile != null && selectedFolder != null) {
                pool.execute(() -> {
                    String fileName = selectedFile.getName();
                    String filePath = selectedFolder.getAbsolutePath();
                    this.process.download(filePath, fileName);
                });
            }  
        });

        VBox fileControl = new VBox(10);
        fileControl.getChildren().addAll(refreshButton, directoryButton, deleteButton);

        VBox folderControl = new VBox(10);
        folderControl.getChildren().addAll(nameBox,mkdirButton, rmdirButton);

        HBox topControl = new HBox(20);
        topControl.getChildren().addAll(fileControl, folderControl); 

        VBox rightLayout = new VBox(10);
        rightLayout.getChildren().addAll(topControl, uploadButton, downloadButton); 
        rightLayout.setPadding(new Insets(10, 10, 10, 10)); 

        this.setRight(rightLayout);
    }

}