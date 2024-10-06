package com.cmj.agendacontactos.datos;

import com.cmj.agendacontactos.dominio.ListaPersonas;

import java.io.*;

public class PersonaDataAccess {
    File archivo;
    FileInputStream fileInput;
    ObjectInputStream input;

    FileOutputStream fileOutput;
    ObjectOutputStream output;

    public PersonaDataAccess() {
        this.archivo = new File(System.getenv("APPDATA") + "\\Agenda de Contactos\\agenda.dat");
        try {
            if(!archivo.exists()) {
                archivo.getParentFile().mkdirs();
                archivo.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void guardarContactos(ListaPersonas personas){
        try {
            fileOutput = new FileOutputStream(archivo);
            output = new ObjectOutputStream(fileOutput);

            output.writeObject(personas);

            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ListaPersonas cargarContactos(){
        try {
            fileInput = new FileInputStream(archivo);
            if(fileInput.available() == 0) throw new RuntimeException("El archivo de agenda está vacío.");
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

    public boolean archivoExiste(){
        return archivo.exists();
    }

    public boolean borrarContactos(){
        return archivo.delete();
    }
}
