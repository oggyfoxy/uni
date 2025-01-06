module org.isep.firstgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.isep.firstgui to javafx.fxml;
    exports org.isep.firstgui;
}