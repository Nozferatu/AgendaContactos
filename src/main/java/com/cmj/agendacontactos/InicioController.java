package com.cmj.agendacontactos;

import com.cmj.agendacontactos.dominio.*;
import com.cmj.agendacontactos.datos.PersonaDataAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

public class InicioController {
    @FXML
    private TextArea inputNombre;
    @FXML
    private TextArea inputApellidos;
    @FXML
    private TextArea inputEmail;
    @FXML
    private TextArea inputTelefono;

    @FXML
    private Label mensajeGuardarContacto;

    @FXML
    private VBox contactos;

    ListaPersonas personas = new ListaPersonas();
    String nombreArchivo = "agenda.dat";
    PersonaDataAccess personaDA = new PersonaDataAccess("./" + nombreArchivo);

    public void cargarDatos(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        String nombre = (String) node.getUserData();
        Persona p = personas.devolverPersona(nombre);
        System.out.println("Contacto cargado: " + p);

        inputNombre.setText(p.getNombre());
        inputApellidos.setText(p.getApellidos());
        inputEmail.setText(p.getEmail());
        inputTelefono.setText(p.getTelefono());
    }

    public void guardarContacto(ActionEvent actionEvent) {
        if (!inputNombre.getText().isEmpty()) {
            Persona p = new Persona(inputNombre.getText(), inputApellidos.getText(), inputEmail.getText(), inputTelefono.getText());
            System.out.println(p);

            personas.agregarPersona(p);
            mensajeGuardarContacto.setText("");
            actualizarListaContactos();
        } else {
            mensajeGuardarContacto.setText("El nombre no puede estar vacío");
        }
    }

    public void actualizarListaContactos() {
        contactos.getChildren().clear();

        Button contacto;
        String nombre;
        for (Persona p : personas.devolverPersonas()) {
            contacto = new Button();
            nombre = p.getNombre();
            contacto.setText(nombre);
            contacto.setUserData(nombre);
            contacto.setPrefWidth(180);
            contacto.setOnAction(this::cargarDatos);
            contactos.getChildren().add(contacto);
        }
    }

    public void guardarAgenda(ActionEvent actionEvent) {
        if(!personas.devolverPersonas().isEmpty()){
            personaDA.guardarContactos(personas);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Agenda guardada correctamente.");
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "No hay ningún contacto añadido.");
            alert.show();
        }
    }

    public void cargarAgenda(ActionEvent actionEvent) {
        try {
            personas = personaDA.cargarContactos();
            actualizarListaContactos();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No hay ningún archivo de agenda.");
            alert.show();
        }
    }

    public void borrarAgenda(ActionEvent actionEvent) {
        personas.borrarPersonas();

        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            archivo.delete();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "La agenda está vacía.");
            alert.show();
        }

        actualizarListaContactos();
    }

    public void confirmarBorrarAgenda(ActionEvent actionEvent) {
        if(!personas.devolverPersonas().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea borrar su agenda?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                borrarAgenda(actionEvent);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "La agenda está vacía.");
            alert.show();
        }
    }
}