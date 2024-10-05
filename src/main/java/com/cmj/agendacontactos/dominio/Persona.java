package com.cmj.agendacontactos.dominio;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class Persona implements Serializable {
    private static final long serialVersionUID = 691539745128821813L;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private HashMap<String, Nota> notas;

    public Persona(String nombre, String apellidos, String email, String telefono){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        notas = new HashMap<>();
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void agregarNota(Nota nota){
        String titulo = nota.getTitulo();
        if(!notas.containsKey(titulo)) notas.put(titulo, nota);
        else notas.replace(titulo, nota);
    }

    public HashMap<String, Nota> getNotas() { return notas; }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(nombre, persona.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nombre);
    }
}
