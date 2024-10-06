package com.cmj.agendacontactos;

import com.cmj.agendacontactos.datos.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    static String resourcesPath = "src/main/resources/com/cmj/agendacontactos";
    Logger logger = new Logger();

    @Override
    public void start(Stage stage) {
        //Pantalla principal
        FXMLLoader inicioLoader = new FXMLLoader(getClass().getResource("inicio.fxml"));
        try {
            Scene principalScene = new Scene(inicioLoader.load(), 1024, 768, Color.ANTIQUEWHITE);
            stage.setResizable(false);
            stage.setTitle("Agenda de Contactos v1");
            Image appIcon = new Image(new File(resourcesPath + "/agenda-icon.png").toURI().toString());
            stage.getIcons().add(appIcon);
            stage.setScene(principalScene);
            stage.show();
        } catch (IOException e) {
            logger.escribirError(e);
        }

    }

    public static void main(String[] args) {
        launch();
    }
}