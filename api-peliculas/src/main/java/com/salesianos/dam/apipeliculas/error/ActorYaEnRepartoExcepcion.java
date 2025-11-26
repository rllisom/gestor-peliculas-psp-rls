package com.salesianos.dam.apipeliculas.error;

public class ActorYaEnRepartoExcepcion extends RuntimeException {
    public ActorYaEnRepartoExcepcion(String message) {
        super(message);
    }

    public ActorYaEnRepartoExcepcion(Long id) {

        super("El actor con id %d ya está en el reparto de la serie".formatted(id));
    }
}
