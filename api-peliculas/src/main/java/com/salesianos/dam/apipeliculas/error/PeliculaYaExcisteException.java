package com.salesianos.dam.apipeliculas.error;

public class PeliculaYaExcisteException extends RuntimeException {

    public PeliculaYaExcisteException(String titulo){
        super("Ya existe una película con el titulo: "+titulo);
    }
}
