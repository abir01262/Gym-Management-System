module com.example.gymapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    requires com.almasb.fxgl.all;
    requires javafx.graphics;
    requires javafx.base;
    requires com.google.gson;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    opens com.gymapp to javafx.base, javafx.fxml, com.google.gson;

    exports com.gymapp;
    exports com.gymapp.net to com.fasterxml.jackson.databind;
}