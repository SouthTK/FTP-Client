package ui;

import javafx.scene.control.Button;

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

    // protected static class CustomBorderPane extends BorderPane {
    //     private void setUpTop() {
    //         VBox topLayout = new VBox(10);
    //         topLayout.setAlignment(Pos.CENTER); 
    //         //topLayout.getChildren().addAll(loginLabel, usernameBox, passwordBox, loginButton, nextLabel, nextButton); 
    //         topLayout.setPadding(new Insets(0, 20, 20, 20)); 
    //         //topLayout.setMaxWidth(300);
    //         topLayout.setMaxHeight(100);
    //         topLayout.setStyle("-fx-background-color: rgba(20, 20, 20, 1); -fx-background-radius: 15;");
    //     }
    // }
}