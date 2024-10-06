package com.cmj.agendacontactos.datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private File archivo;
    private PrintWriter output;

    public Logger(){
        this.archivo = new File(System.getenv("APPDATA") + "\\Agenda de Contactos\\log.txt");
        try {
            if(!archivo.exists()) {
                archivo.getParentFile().mkdirs();
                archivo.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void escribirError(Exception e){
        try {
            e.printStackTrace();
            output = new PrintWriter(archivo);
            output.write(e.getMessage());
            output.close();
        } catch (FileNotFoundException ex) { throw new RuntimeException(ex); }
    }
}
