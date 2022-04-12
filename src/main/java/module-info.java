module com.ija.Application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.json;

    opens com.ija.Application to javafx.fxml;
    exports com.ija.Application;
    exports com.ija.GUI;
    opens com.ija.GUI to javafx.fxml;
}