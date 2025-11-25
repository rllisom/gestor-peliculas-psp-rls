package com.salesianos.dam.apipeliculas.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalHandleException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadNoEncontradaExcepcion.class)
    public ProblemDetail handleEntidadNoEncontrada(EntidadNoEncontradaExcepcion ex){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        problem.setTitle("Entidad no encontrada");
        problem.setType(URI.create("gestorpeliculas.com/error/no-encontrado"));

        return problem;
    }
    @ExceptionHandler(DirectorMenorEdadException.class)
    public ProblemDetail handleEdadDirector(DirectorMenorEdadException ex){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Error en el atributo");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("gestorpeliculas.com/error/argumento-erroneo"));

        return problem;
    }

    @ExceptionHandler(ActorYaEnRepartoExcepcion.class)
    public ProblemDetail handlerActorEnRepartoExcepcion(ActorYaEnRepartoExcepcion ex){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);

        problem.setTitle("Error al asignar");
        problem.setDetail(ex.getMessage());
        problem.setType(URI.create("gestorpeliculas.com/error/fallo-agregar"));

        return problem;
    }

    @ExceptionHandler(PeliculaYaExcisteException.class)
    public ProblemDetail handlePeliculaExistente(PeliculaYaExcisteException ex){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,ex.getMessage());
        problem.setTitle("Película ya existente");
        problem.setType(URI.create("gestorpeliculas.com/error/fallo-agregar"));

        return problem;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleArgumentoIlegal(IllegalArgumentException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Error en los argumentos");
        problem.setType(URI.create("gestorpeliculas.com/error/argumento-erroneo"));

        return problem;
    }

}
