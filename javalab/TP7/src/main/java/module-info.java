module org.isep.tp7 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.isep.tp7 to javafx.fxml;
    exports org.isep.tp7;
}