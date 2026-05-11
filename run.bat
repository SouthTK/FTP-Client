cd src
javac --module-path "../lib/javafx-sdk-17.0.17/lib;." --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web App.java
java --module-path "../lib/javafx-sdk-17.0.17/lib;." --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web App
cd ..

:: -Xlint:unchecked