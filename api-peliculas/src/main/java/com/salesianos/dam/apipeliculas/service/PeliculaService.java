package com.salesianos.dam.apipeliculas.service;

import com.salesianos.dam.apipeliculas.dto.PeliculaRequestDTO;
import com.salesianos.dam.apipeliculas.error.*;
import com.salesianos.dam.apipeliculas.model.Actor;
import com.salesianos.dam.apipeliculas.model.Director;
import com.salesianos.dam.apipeliculas.model.Pelicula;
import com.salesianos.dam.apipeliculas.repository.ActorRepository;
import com.salesianos.dam.apipeliculas.repository.DirectorRespository;
import com.salesianos.dam.apipeliculas.repository.PeliculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final DirectorRespository directorRespository;
    private final ActorRepository actorRepository;

    public List<Pelicula> getAll(){
        List<Pelicula> result = peliculaRepository.findAll();

        if(result.isEmpty()) throw new PeliculaNoEncontradaExcepcion();

        return result;
    }

    public Pelicula getById(Long id){
        return peliculaRepository.findById(id).orElseThrow(() -> new PeliculaNoEncontradaExcepcion(id));
    }

    public Pelicula create(PeliculaRequestDTO dto){
        if(!StringUtils.hasText(dto.titulo())) throw new IllegalArgumentException("Falta el campo del título de la película");
        if(peliculaRepository.existsByTitulo(dto.titulo())) throw new PeliculaYaExcisteException(dto.titulo());
        Director d = directorRespository.findById(dto.dir_id()).orElseThrow(() -> new DirectorNoEncontradoException(dto.dir_id()));
        return peliculaRepository.save(dto.toEntity(d));
    }

    public Pelicula edit(Long idPelicula,PeliculaRequestDTO dto, Long actorId){

        if(!StringUtils.hasText(dto.titulo())) throw new IllegalArgumentException("Falta el campo del título de la película");
        if(peliculaRepository.existsByTitulo(dto.titulo())) throw new PeliculaYaExcisteException(dto.titulo());

        Actor a = actorRepository.findById(actorId).orElseThrow(() -> new ActorNoEncontradoException(actorId));

        return peliculaRepository.findById(idPelicula)
                .map( p -> {
                            p.setTitulo(dto.titulo());
                            p.setGenero(dto.genero());
                            p.setFechaEstreno(dto.fechaEstreno());
                            p.setDirector(directorRespository.findById(dto.dir_id()).orElseThrow(()->new DirectorNoEncontradoException(dto.dir_id())));

                            if(!p.getActores().contains(a)){
                                p.getActores().add(a);
                            }else{
                                throw new ActorYaEnRepartoExcepcion(actorId);
                            }

                            return peliculaRepository.save(p);
                }).orElseThrow(() -> new PeliculaNoEncontradaExcepcion(idPelicula));

    }

    public void delete(Long id){
        Pelicula p = peliculaRepository.findById(id).orElseThrow(() -> new PeliculaNoEncontradaExcepcion(id));
        peliculaRepository.deleteById(id);
    }
}
