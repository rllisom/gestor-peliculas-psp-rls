package com.salesianos.dam.apipeliculas.error;

public class PeliculaYaExcisteException extends RuntimeException {
    public PeliculaYaExcisteException(String message) {
        super(message);
    }
}
