package com.salesianos.dam.apipeliculas.controller;

import com.salesianos.dam.apipeliculas.dto.ActorRequestDTO;
import com.salesianos.dam.apipeliculas.dto.ActorResponseDTO;
import com.salesianos.dam.apipeliculas.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequestMapping("/api/v1/actores")
@RequiredArgsConstructor
@RestController
public class ActorController {

    private final ActorService actorService;

    @GetMapping
    public List<ActorResponseDTO> getAll(){
        return actorService.getAll().stream().map(ActorResponseDTO::of).toList();
    }

    @GetMapping("/{id}")
    public ActorResponseDTO getById(@PathVariable Long id){
        return ActorResponseDTO.of(actorService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ActorResponseDTO> create (@RequestBody ActorRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ActorResponseDTO.of(actorService.create(dto)));
    }
}
