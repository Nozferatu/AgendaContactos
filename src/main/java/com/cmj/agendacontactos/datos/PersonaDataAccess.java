package com.cmj.agendacontactos.datos;

import com.cmj.agendacontactos.dominio.ListaPersonas;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PersonaDataAccess {
    String nombreArchivo;
    FileInputStream fileInput;
    ObjectInputStream input;

    FileOutputStream fileOutput;
    ObjectOutputStream output;

    public PersonaDataAccess(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void guardarContactos(ListaPersonas personas){
        try {
            fileOutput = new FileOutputStream(nombreArchivo);
            output = new ObjectOutputStream(fileOutput);

            output.writeObject(personas);

            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ListaPersonas cargarContactos(){
        try {
            fileInput = new FileInputStream(nombreArchivo);
            input = new ObjectInputStream(fileInput);

            ListaPersonas lista = (ListaPersonas) input.readObject();

            input.close();

            return lista;
        } catch (IOException e) {
            String nombreEx = e.getClass().getSimpleName();
            switch (nombreEx){
                case "FileNotFoundException":
                    throw new RuntimeException("No se ha encontrado el archivo de agenda.");
                case "EOFException":
                    throw new RuntimeException("El archivo de agenda no es válido o está corrupto.");
                default:
                    throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
