package com.cmj.agendacontactos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    static String resourcesPath = "src/main/resources/com/cmj/agendacontactos";

    @Override
    public void start(Stage stage) throws IOException {
        //Pantalla principal
        FXMLLoader inicioLoader = new FXMLLoader(getClass().getResource("inicio.fxml"));
        Scene principalScene = new Scene(inicioLoader.load(), 1024, 768, Color.ANTIQUEWHITE);
        InicioController inicioController = (InicioController) inicioLoader.getController();

        //Editor de notas
        FXMLLoader editorNotasLoader = new FXMLLoader(getClass().getResource("editorNota.fxml"));
        Scene editorNotaScene = new Scene(editorNotasLoader.load(), 1024, 768, Color.ANTIQUEWHITE);
        EditorNotaController editorNotaController = (EditorNotaController) editorNotasLoader.getController();

        //Asignando las escenas a los controladores
        inicioController.establecerPantallaEditorNota(editorNotaScene);
        editorNotaController.establecerPantallaPrincipal(principalScene);

        stage.setResizable(false);
        stage.setTitle("Agenda de Contactos v1");
        Image appIcon = new Image(new File(resourcesPath + "/agenda-icon.png").toURI().toString());
        stage.getIcons().add(appIcon);
        stage.setScene(principalScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}