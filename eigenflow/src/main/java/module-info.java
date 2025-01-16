module org.isep.eigenflow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.slf4j.simple;
    requires javafx.graphics;

    exports org.isep.eigenflow.ui;
    
    opens org.isep.eigenflow.ui to javafx.fxml;
    opens org.isep.eigenflow.domain to javafx.base;

}