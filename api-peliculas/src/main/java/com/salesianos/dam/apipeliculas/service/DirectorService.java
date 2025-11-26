package com.salesianos.dam.apipeliculas.service;

import com.salesianos.dam.apipeliculas.dto.DirectorRequestDTO;
import com.salesianos.dam.apipeliculas.error.DirectorMenorEdadException;
import com.salesianos.dam.apipeliculas.error.DirectorNoEncontradoException;
import com.salesianos.dam.apipeliculas.model.Director;
import com.salesianos.dam.apipeliculas.repository.DirectorRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class DirectorService {

    private static final Long director_desc = 0L;
    private final DirectorRespository directorRespository;

    public List<Director> getAll(){
        return directorRespository.findAll();
    }

    public Director getById(Long id){
        return directorRespository.findById(id).orElseThrow(() -> new DirectorNoEncontradoException(id));
    }

    public Director create(DirectorRequestDTO dto){

        DirectorRequestDTO.validarDTO(dto);
        Director.comprobarEdad(dto.anioNacimiento());

        return directorRespository.save(dto.toEntity());
    }

    public Director edit(Long id,DirectorRequestDTO dto){

        DirectorRequestDTO.validarDTO(dto);
        Director.comprobarEdad(dto.anioNacimiento());

        return directorRespository.findById(id)
                .map(d -> {
                    d.setNombre(dto.nombre());
                    d.setAnioNacimiento(dto.anioNacimiento());

                    return directorRespository.save(d);
                })
                .orElseThrow(() -> new DirectorNoEncontradoException(id));
    }

    public void delete(Long id){
        if(id.equals(director_desc)) throw new IllegalArgumentException("No se puede eliminar");

        Director d = directorRespository.findById(id).orElseThrow(()-> new DirectorNoEncontradoException(id));
        Director desc = directorRespository.findById(0L).orElseThrow(() -> new DirectorNoEncontradoException("No existes en la base de datos dicho director"));

        d.eliminarDirector(desc);

        directorRespository.delete(d);
    }
}
