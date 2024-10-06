package com.cmj.agendacontactos;

import com.cmj.agendacontactos.dominio.Nota;
import com.cmj.agendacontactos.dominio.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EditorNotaController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextArea tituloArea;

    @FXML
    private TextField contenidoArea;

    @FXML
    private Button botonGuardar;

    @FXML
    private Label mensajeBotonGuardar;

    static private Persona personaActiva;


    public void cargarDatosNota(Stage stage){
        Nota nota = (Nota) stage.getUserData();
        tituloArea.setText(nota.getTitulo());
        contenidoArea.setText(nota.getContenido());
    }

    public void guardarNota(ActionEvent actionEvent){
        String titulo = tituloArea.getText();
        String contenido = contenidoArea.getText();

        if(!titulo.isEmpty()){
            Nota nota = new Nota(titulo, contenido);
            personaActiva.agregarNota(nota);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("inicio.fxml"));

            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            InicioController inicioController = loader.getController();
            inicioController.actualizarPersona(personaActiva);

            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setUserData(new Nota());

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            inicioController.actualizarListaContactos();
        }else{
            mensajeBotonGuardar.setText("El título no debe de estar vacío.");
        }
    }

    public void seleccionarPersona(Persona p){
        personaActiva = p;
    }
}
