module org.isep.eigenflow {
    requires javafx.controls;
    requires javafx.fxml;

    // this line is crucial - it lets javafx see your classes
    exports org.isep.eigenflow.ui;
    // also need this for fxml to work with your controller
    opens org.isep.eigenflow.ui to javafx.fxml;
}