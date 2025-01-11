module org.isep.eigenflow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.slf4j.simple;
    requires javafx.graphics;

    // this line is crucial - it lets javafx see your classes
    exports org.isep.eigenflow.ui;
    // also need this for fxml to work with your controller
    opens org.isep.eigenflow.ui to javafx.fxml;
    opens org.isep.eigenflow.domain to javafx.base;

}