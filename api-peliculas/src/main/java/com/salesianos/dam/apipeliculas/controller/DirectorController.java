package com.salesianos.dam.apipeliculas.controller;

import com.salesianos.dam.apipeliculas.dto.DirectorRequestDTO;
import com.salesianos.dam.apipeliculas.dto.DirectorResponseDTO;
import com.salesianos.dam.apipeliculas.model.Director;
import com.salesianos.dam.apipeliculas.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/director")
@RestController
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    public List<DirectorResponseDTO> getAll(){
        return directorService.getAll().stream().map(DirectorResponseDTO::of).toList();
    }

    @GetMapping("/{id}")
    public DirectorResponseDTO getById(@PathVariable Long id){
        return DirectorResponseDTO.of(directorService.getById(id));
    }

    @PostMapping
    public ResponseEntity<DirectorResponseDTO> create(@RequestBody DirectorRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(DirectorResponseDTO.of(directorService.create(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectorResponseDTO> edit(@PathVariable Long id, @RequestBody DirectorRequestDTO dto){
        return ResponseEntity.ok(DirectorResponseDTO.of(directorService.edit(id,dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        directorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
