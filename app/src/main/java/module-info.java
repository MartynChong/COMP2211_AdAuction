module org.comp2211 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.opencsv;
    requires org.apache.logging.log4j;
    exports org.comp2211;
    exports org.comp2211.controllers;
    exports org.comp2211.model;
}
