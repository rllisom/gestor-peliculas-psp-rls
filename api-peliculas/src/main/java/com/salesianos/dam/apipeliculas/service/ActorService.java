package com.salesianos.dam.apipeliculas.service;

import com.salesianos.dam.apipeliculas.dto.ActorRequestDTO;
import com.salesianos.dam.apipeliculas.error.ActorNoEncontradoException;
import com.salesianos.dam.apipeliculas.model.Actor;
import com.salesianos.dam.apipeliculas.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ActorService {

    private final ActorRepository actorRepository;

    public List<Actor> getAll(){
        List<Actor> result = actorRepository.findAll();
        if(result.isEmpty()) throw new ActorNoEncontradoException();
        return result;
    }

    public Actor getById(Long id){
        return actorRepository.findById(id).orElseThrow(() -> new ActorNoEncontradoException(id));
    }

    public Actor create(ActorRequestDTO dto){
        if(!StringUtils.hasText(dto.nombre())) throw new IllegalArgumentException("Falta el campo nombre para crear al actor");
        return actorRepository.save(dto.toEntity());
    }
}
