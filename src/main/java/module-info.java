module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jgrapht.core;

    opens App to javafx.fxml;
    exports App;
}