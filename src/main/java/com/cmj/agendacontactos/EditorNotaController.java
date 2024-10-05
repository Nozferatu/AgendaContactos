package com.cmj.agendacontactos;

import com.cmj.agendacontactos.dominio.Nota;
import com.cmj.agendacontactos.dominio.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditorNotaController {
    private Scene pantallaPrincipal;

    @FXML
    private TextArea tituloArea;

    @FXML
    private TextField contenidoArea;

    @FXML
    private Button botonGuardar;

    @FXML
    private Label mensajeBotonGuardar;

    static private Persona personaActiva;

    public void establecerPantallaPrincipal(Scene scene){
        pantallaPrincipal = scene;
    }

    public void volverPantallaPrincipal(ActionEvent actionEvent){
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(pantallaPrincipal);
        stage.show();
    }

    public void cargarDatosNota(String titulo, String contenido){
        tituloArea.setText(titulo);
        contenidoArea.setText(contenido);
    }

    public void guardarNota(ActionEvent actionEvent){
        String titulo = tituloArea.getText();
        String contenido = contenidoArea.getText();

        if(!titulo.isEmpty()){
            Nota nota = new Nota(titulo, contenido);
            personaActiva.agregarNota(nota);
            volverPantallaPrincipal(actionEvent);
        }else{
            mensajeBotonGuardar.setText("El título no debe de estar vacío.");
        }
    }

    public void seleccionarPersona(Persona p){
        personaActiva = p;
    }
}
