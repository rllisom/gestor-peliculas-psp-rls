package com.salesianos.dam.apipeliculas.repository;

import com.salesianos.dam.apipeliculas.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula,Long> {
}
