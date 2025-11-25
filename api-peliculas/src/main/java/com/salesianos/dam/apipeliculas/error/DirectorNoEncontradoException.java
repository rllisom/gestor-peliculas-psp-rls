package com.salesianos.dam.apipeliculas.error;

public class DirectorNoEncontradoException extends RuntimeException {
    public DirectorNoEncontradoException(String message) {
        super(message);
    }

    public DirectorNoEncontradoException(){
        super("No se han encontrado directores en el servidor");
    }

    public DirectorNoEncontradoException(Long id){
        super("No se ha encontrado al director con id %d".formatted(id));
    }
}
