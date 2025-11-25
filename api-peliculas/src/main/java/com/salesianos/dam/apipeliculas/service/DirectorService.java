package com.salesianos.dam.apipeliculas.service;

import com.salesianos.dam.apipeliculas.dto.DirectorRequestDTO;
import com.salesianos.dam.apipeliculas.error.DirectorMenorEdadException;
import com.salesianos.dam.apipeliculas.error.DirectorNoEncontradoException;
import com.salesianos.dam.apipeliculas.model.Director;
import com.salesianos.dam.apipeliculas.repository.DirectorRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DirectorService {

    private final DirectorRespository directorRespository;

    public List<Director> getAll(){
        return directorRespository.findAll();
    }

    public Director getById(Long id){
        return directorRespository.findById(id).orElseThrow(() -> new DirectorNoEncontradoException(id));
    }

    public Director create(DirectorRequestDTO dto){
        if(!StringUtils.hasText(dto.nombre())) throw new IllegalArgumentException("Falta el campo del nombre del director");
        if(dto.anioNacimiento()<18) throw new DirectorMenorEdadException("El director no puede ser menor de edad");
        return directorRespository.save(dto.toEntity());
    }

    public Director edit(Long id,DirectorRequestDTO dto){
        if(!StringUtils.hasText(dto.nombre())) throw new IllegalArgumentException("Falta el campo del nombre del director");
        if(dto.anioNacimiento()<18) throw new DirectorMenorEdadException("El director no puede ser menor de edad");

        return directorRespository.findById(id)
                .map(d -> {
                    d.setNombre(dto.nombre());
                    d.setAnioNacimiento(dto.anioNacimiento());

                    return directorRespository.save(d);
                })
                .orElseThrow(() -> new DirectorNoEncontradoException(id));
    }

    public void delete(Long id){
        Director d = directorRespository.findById(id).orElseThrow(()-> new DirectorNoEncontradoException(id));
        directorRespository.delete(d);
    }
}
