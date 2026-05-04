package ui;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;

import javafx.scene.layout.BorderPane;

public class Component {
    protected static class CustomButton extends Button {
        public CustomButton() {
            super();
            this.setStyle("-fx-background-color: rgba(30, 30, 30, 1);" +
                    "-fx-text-fill: white;" +
                    "-fx-border-color: rgba(30, 30, 30, 1);" +
                    "-fx-border-width: 0.5px;");
            this.setOnMouseEntered(e -> {
            this.setStyle("-fx-background-color: rgba(30, 30, 30, 1);" +
                    "-fx-text-fill: white;" + 
                    "-fx-border-color: white;" +
                    "-fx-border-width: 0.5px;");
            });
            this.setOnMouseExited(e -> {
                this.setStyle("-fx-background-color: rgba(30, 30, 30, 1);" +
                    "-fx-text-fill: white;" +
                    "-fx-border-color: rgba(30, 30, 30, 1);" +
                    "-fx-border-width: 0.5px;");
            });
        }
    }

    protected static class CustomDropDown extends ComboBox<String> {
        public CustomDropDown() {
            super();
            this.setStyle("-fx-background-color: rgba(30, 30, 30, 1);" +
                    "-fx-text-fill: white;" +
                    "-fx-border-color: rgba(30, 30, 30, 1);" +
                    "-fx-border-width: 0.5px;");
            this.setOnMouseEntered(e -> {
            this.setStyle("-fx-background-color: rgba(30, 30, 30, 1);" +
                    "-fx-text-fill: white;" + 
                    "-fx-border-color: white;" +
                    "-fx-border-width: 0.5px;");
            });
            this.setOnMouseExited(e -> {
                this.setStyle("-fx-background-color: rgba(30, 30, 30, 1);" +
                    "-fx-text-fill: white;" +
                    "-fx-border-color: rgba(30, 30, 30, 1);" +
                    "-fx-border-width: 0.5px;");
            });
        }
    }
}