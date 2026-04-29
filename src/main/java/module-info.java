module com.combate {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires transitive javafx.graphics;
    requires java.desktop;

    opens com.combate.controllers to javafx.fxml;
    opens com.combate.model to com.google.gson;
    exports com.combate;
}