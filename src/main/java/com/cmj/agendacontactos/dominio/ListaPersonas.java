package com.cmj.agendacontactos.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ListaPersonas implements Serializable {
    private static final long serialVersionUID = 691539745128821857L;

    private HashMap<String, Persona> personas;

    public ListaPersonas(){
        personas = new HashMap<>();
    }

    public void agregarPersona(Persona p){
        if(!personas.containsValue(p)){
            System.out.println("No existe");
            personas.put(p.getNombre(), p);
        }else{
            System.out.println("Ya existe");
            personas.replace(p.getNombre(), p);
        }
    }

    public Persona devolverPersona(String nombre){
        return personas.get(nombre);
    }

    public Collection<Persona> devolverPersonaSegunLetra(char letra){
        Collection<Persona> personasFiltradas = new ArrayList<>();

        for(Persona p: personas.values()){
            if(p.getNombre().toLowerCase().charAt(0) == letra) personasFiltradas.add(p);
        }

        return personasFiltradas;
    }

    public Collection<Persona> devolverPersonas(){
        return personas.values();
    }

    public void borrarPersonas(){
        personas.clear();
    }
}
