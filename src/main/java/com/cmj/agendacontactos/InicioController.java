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
    private Label mensajeGuardarArchivo;
    @FXML
    private Label mensajeBorrarAgenda;

    @FXML
    private VBox contactos;

    ListaPersonas personas = new ListaPersonas();
    String nombreArchivo = "personas.dat";
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
            mensajeGuardarArchivo.setText("");
            actualizarListaContactos();
        } else {
            mensajeGuardarArchivo.setText("El nombre no puede estar vacío");
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

    public void guardarArchivo(ActionEvent actionEvent) {
        personaDA.guardarContactos(personas);
    }

    public void cargarArchivo(ActionEvent actionEvent) {
        personas = personaDA.cargarContactos();
        actualizarListaContactos();
    }

    public void borrarAgenda(ActionEvent actionEvent) {
        personas.borrarPersonas();
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "La agenda está vacía.");
            alert.show();
        }
    }

    public void borrarArchivo(ActionEvent actionEvent) {
        File archivo = new File(nombreArchivo);
        if (archivo.exists()) {
            archivo.delete();
        } else {
            mensajeBorrarAgenda.setText("No existe ningún archivo");
        }
    }
}