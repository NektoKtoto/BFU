module release.gameoflife {
    requires javafx.controls;
    requires javafx.fxml;


    opens release.gameoflife to javafx.fxml;
    exports release.gameoflife;
}