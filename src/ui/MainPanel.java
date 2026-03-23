package ui;

import javafx.scene.control.TextArea;
import javafx.scene.control.Button;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javafx.geometry.Insets;

public class MainPanel extends BorderPane {
    public MainPanel() {     
        this.setUpBottom(this);
        this.setStyle("-fx-background-color: rgba(20, 20, 20, 1);");
    }

    private void setUpBottom(BorderPane mainPane) {
        //output box
        TextArea outputBox = new TextArea();
        outputBox.setStyle("-fx-control-inner-background: rgba(30, 30, 30, 1);");
        outputBox.setEditable(false); 
        outputBox.setPromptText("Output will appear here...");
        outputBox.appendText("A new message.\n");
        for(int i = 0; i < 20; i++) {
            outputBox.appendText("A new message.\n");
        }

        VBox bottomLayout = new VBox(10);
        bottomLayout.getChildren().addAll(outputBox); 
        bottomLayout.setPadding(new Insets(0, 10, 10, 10)); 

        mainPane.setBottom(bottomLayout);
    }
}