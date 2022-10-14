module com.example.javaend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.javaend to javafx.fxml;
    exports com.example.javaend;
    exports com.example.javaend.Models;
    opens com.example.javaend.Models to javafx.fxml;
}