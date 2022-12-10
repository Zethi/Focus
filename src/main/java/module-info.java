module com.github.zethi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens com.github.zethi to javafx.fxml;
    exports com.github.zethi;
}