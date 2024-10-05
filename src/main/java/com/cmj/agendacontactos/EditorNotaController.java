package com.cmj.agendacontactos;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditorNotaController {
    @FXML
    TextArea tituloArea;

    @FXML
    TextField contenidoArea;

    public void cargarDatosNota(String titulo, String contenido){
        tituloArea.setText(titulo);
        contenidoArea.setText(contenido);
    }
}
