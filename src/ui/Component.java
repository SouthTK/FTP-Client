package ui;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import javafx.scene.text.Font;

import javafx.scene.layout.BorderPane;

import javafx.beans.property.SimpleStringProperty;

public class Component {
    public static class CustomButton extends Button {
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

    public static class CustomDropDown extends ComboBox<String> {
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

    public static class CustomView extends TableView<FileInfo> {
        public CustomView() {
            super();
            TableColumn<FileInfo, String> permissionColumn = new TableColumn<>("Permission");
            permissionColumn.setCellValueFactory(cellData -> cellData.getValue().permissionProperty());
            permissionColumn.setMinWidth(90);
            permissionColumn.setMaxWidth(90);
            TableColumn<FileInfo, String> linksColumn = new TableColumn<>("Links");
            linksColumn.setCellValueFactory(cellData -> cellData.getValue().linksProperty());
            linksColumn.setMinWidth(50);
            linksColumn.setMaxWidth(50);
            TableColumn<FileInfo, String> ownerColumn = new TableColumn<>("Owner");
            ownerColumn.setCellValueFactory(cellData -> cellData.getValue().ownerProperty());
            ownerColumn.setPrefWidth(100);
            ownerColumn.setMinWidth(100);
            ownerColumn.setMaxWidth(100);
            TableColumn<FileInfo, String> groupColumn = new TableColumn<>("Group");
            groupColumn.setCellValueFactory(cellData -> cellData.getValue().groupProperty());
            groupColumn.setMinWidth(100);
            groupColumn.setMaxWidth(100);
            TableColumn<FileInfo, String> sizeColumn = new TableColumn<>("Size");
            sizeColumn.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
            sizeColumn.setMinWidth(80);
            sizeColumn.setMaxWidth(80);
            TableColumn<FileInfo, String> timeColumn = new TableColumn<>("Time");
            timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
            timeColumn.setMinWidth(90);
            timeColumn.setMaxWidth(90);
            TableColumn<FileInfo, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            
            this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            this.getColumns().addAll(permissionColumn, linksColumn, ownerColumn, groupColumn,
                    sizeColumn, timeColumn, nameColumn);
        }
    }

    public static class FileInfo {
        private final SimpleStringProperty permission;
        private final SimpleStringProperty links;
        private final SimpleStringProperty owner;
        private final SimpleStringProperty group;
        private final SimpleStringProperty size;
        private final SimpleStringProperty time;
        private final SimpleStringProperty name;

        public FileInfo(String permission, String links, String owner, String group, 
                String size, String time, String name) {
            this.permission = new SimpleStringProperty(permission);
            this.links = new SimpleStringProperty(links);
            this.owner = new SimpleStringProperty(owner);
            this.group = new SimpleStringProperty(group);
            this.size = new SimpleStringProperty(size); 
            this.time = new SimpleStringProperty(time);
            this.name = new SimpleStringProperty(name);
        }

        public SimpleStringProperty permissionProperty() {return this.permission;}

        public SimpleStringProperty linksProperty() {return this.links;}

        public SimpleStringProperty ownerProperty() {return this.owner;}

        public SimpleStringProperty groupProperty() {return this.group;}

        public SimpleStringProperty timeProperty() {return this.time;}

        public SimpleStringProperty sizeProperty() {return this.size;}

        public SimpleStringProperty nameProperty() {return this.name;}

        public String getName() {
            if (this.permission.get().charAt(0) == 'l') {
                String[] parts = this.name.get().split("\\s+");
                String name = parts[0];
                for (int i = 1; i < parts.length; i++) {
                    if (parts[i].equals("->")) {break;}
                    name += parts[i];
                }
                return name;
            } 
            return this.name.get();
        }
    }
}   