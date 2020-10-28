module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}