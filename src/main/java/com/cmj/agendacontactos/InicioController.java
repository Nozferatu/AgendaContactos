package com.cmj.agendacontactos;

import com.cmj.agendacontactos.dominio.*;
import com.cmj.agendacontactos.datos.PersonaDataAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    GridPane gridLetrasBusqueda;
    String letras = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ#";

    static ListaPersonas personas = new ListaPersonas();
    PersonaDataAccess personaDA = new PersonaDataAccess();

    public void initialize(){
        Button botonLetra;
        String letra;

        int fila;
        int maxFila = gridLetrasBusqueda.getRowCount();
        int columna = 0;


        for(int i = 0; i < letras.length(); i++){
            fila = i;
            if(i == maxFila) {
                columna = 1;
            }
            if(columna == 1) fila = i - maxFila;

            letra = "" + letras.charAt(i);
            botonLetra = new Button();
            botonLetra.setText(letra);
            botonLetra.setUserData(letra);
            botonLetra.setPrefWidth(40);
            botonLetra.setOnAction(this::buscarPorLetra);
            gridLetrasBusqueda.add(botonLetra, columna, fila);
        }
    }

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

    public void guardarContacto() {
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

    public void abrirEditorNotas(ActionEvent actionEvent){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editorNota.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        EditorNotaController editorNotaController = loader.getController();
        MenuItem item = (MenuItem) actionEvent.getSource();
        String accion = item.getId();
        Persona p = personas.devolverPersona((String) item.getUserData());
        editorNotaController.seleccionarPersona(p);

        stage = (Stage) item.getParentPopup().getOwnerWindow();
        stage.setUserData(new Nota());

        switch(accion){
            case "agregarNota":
                editorNotaController.cargarDatosNota(stage);

                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            case "verNotas":
                if(!p.getNotas().isEmpty()){
                    List<String> titulos = p.getNotas().keySet().stream().toList();
                    ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(titulos.get(0), titulos);

                    Optional<String> eleccion = choiceDialog.showAndWait();

                    if(eleccion.isPresent()){
                        Nota notaElegida = p.getNotas().get(eleccion.get());
                        stage.setUserData(notaElegida);

                        editorNotaController.cargarDatosNota(stage);

                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No hay ninguna nota guardada.");
                    alert.show();
                }
        }


    }

    public void actualizarPersona(Persona p){
        personas.agregarPersona(p);
    }

    public void actualizarListaContactos(){
        actualizarListaContactos(false, ' ');
    }

    public void actualizarListaContactos(boolean filtrar, char letra) {
        System.out.println(personas.devolverPersonas());
        contactos.getChildren().clear();

        Collection<Persona> personasAux;
        Button contacto;
        String nombre;
        ContextMenu contextMenu;
        MenuItem itemAgregarNota;
        MenuItem itemVerNotas;

        if(filtrar && letra != '#') personasAux = personas.devolverPersonaSegunLetra(letra);
        else personasAux = personas.devolverPersonas();

        for (Persona p : personasAux) {
            contacto = new Button();
            nombre = p.getNombre();
            contacto.setText(nombre);
            contacto.setUserData(nombre);
            contacto.setPrefWidth(180);
            contacto.setOnAction(this::cargarDatos);

            contextMenu = new ContextMenu();
            itemAgregarNota = new MenuItem("Agregar nota");
            itemAgregarNota.setId("agregarNota");
            itemAgregarNota.setUserData(nombre);
            itemAgregarNota.setOnAction(this::abrirEditorNotas);
            itemVerNotas = new MenuItem("Ver notas");
            itemVerNotas.setId("verNotas");
            itemVerNotas.setUserData(nombre);
            itemVerNotas.setOnAction(this::abrirEditorNotas);

            contextMenu.getItems().addAll(itemAgregarNota, itemVerNotas);
            contacto.setContextMenu(contextMenu);

            contactos.getChildren().add(contacto);
        }
    }

    public void buscarPorLetra(ActionEvent actionEvent){
        Button botonLetra = (Button) actionEvent.getSource();
        char letra = botonLetra.getText().toLowerCase().charAt(0);
        actualizarListaContactos(true, letra);
    }

    public void guardarAgenda() {
        if(!personas.devolverPersonas().isEmpty()){
            personaDA.guardarContactos(personas);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Agenda guardada correctamente.");
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "No hay ningún contacto añadido.");
            alert.show();
        }
    }

    public void cargarAgenda() {
        try {
            personas = personaDA.cargarContactos();
            actualizarListaContactos();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }

    public void borrarAgenda() {
        personas.borrarPersonas();

        boolean archivoExiste = personaDA.archivoExiste();
        if (archivoExiste) {
            personaDA.borrarContactos();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "La agenda está vacía.");
            alert.show();
        }

        actualizarListaContactos();
    }

    public void confirmarBorrarAgenda() {
        if(!personas.devolverPersonas().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea borrar su agenda?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                borrarAgenda();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "La agenda está vacía.");
            alert.show();
        }
    }

    public void mostrarInfoApp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información sobre Agenda de Contactos");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image iconoAgenda = new Image(new File(HelloApplication.resourcesPath + "/agenda-icon.png").toURI().toString());
        stage.getIcons().add(iconoAgenda);
        ImageView iconoHeader = new ImageView(iconoAgenda);
        iconoHeader.setFitHeight(48);
        iconoHeader.setFitWidth(48);
        alert.getDialogPane().setGraphic(iconoHeader);
        alert.setHeaderText("Agenda de Contactos");
        alert.setContentText("Versión: 1.0\nAutor: Carlos Madrid Jiménez");
        alert.show();
    }
}