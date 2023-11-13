module no.njoter.casefront {
    requires javafx.controls;
    requires javafx.fxml;


    opens no.njoter.casefront to javafx.fxml;
    exports no.njoter.casefront;
}