package com.salesianos.dam.apipeliculas.error;

public class PeliculaNoEncontradaExcepcion extends EntidadNoEncontradaExcepcion{
    public PeliculaNoEncontradaExcepcion(String message) {
        super(message);
    }

    public PeliculaNoEncontradaExcepcion(){
        super("No se han encontrado películas en el servidor");
    }

    public PeliculaNoEncontradaExcepcion(Long id){
        super("No se ha encontrado la película con id %d".formatted(id));
    }
}
