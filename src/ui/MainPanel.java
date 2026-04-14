package ui;

import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class MainPanel extends BorderPane {
    public MainPanel() {   
        this.setUpTop();  
        this.setUpBottom();
        this.setStyle("-fx-background-color: rgba(20, 20, 20, 1);");
    }

    private void setUpBottom() {
        //output box
        TextArea outputBox = new TextArea();
        outputBox.setStyle("-fx-control-inner-background: rgba(30, 30, 30, 1);");
        outputBox.setEditable(false); 
        outputBox.setPromptText("Output will appear here...");
        //outputBox.appendText("A new message.\n");
        for(int i = 0; i < 20; i++) {
            outputBox.appendText("Test message.\n");
        }

        VBox bottomLayout = new VBox(10);
        bottomLayout.getChildren().addAll(outputBox); 
        bottomLayout.setPadding(new Insets(0, 10, 10, 10)); 

        this.setBottom(bottomLayout);
    }

    private void setUpTop() {
        VBox topLayout = new VBox(10);
        topLayout.setAlignment(Pos.CENTER); 
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
}