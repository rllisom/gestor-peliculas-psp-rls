package com.salesianos.dam.apipeliculas.error;

public class ActorNoEncontradoException extends EntidadNoEncontradaExcepcion{
    public ActorNoEncontradoException(String message) {
        super(message);
    }

    public ActorNoEncontradoException(){
        super("No se han encontrado actores en el servidor");
    }

    public ActorNoEncontradoException(Long id){
        super("No se ha encontrado al actor con id %d".formatted(id));
    }
}
