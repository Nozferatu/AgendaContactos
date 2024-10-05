package com.cmj.agendacontactos;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    static String resourcesPath = "src/main/resources/com/cmj/agendacontactos";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768, Color.ANTIQUEWHITE);
        stage.setResizable(false);
        stage.setTitle("Agenda de Contactos v1");
        Image appIcon = new Image(new File(resourcesPath + "/agenda-icon.png").toURI().toString());
        stage.getIcons().add(appIcon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}