package com.salesianos.dam.apipeliculas.repository;

import com.salesianos.dam.apipeliculas.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRespository extends JpaRepository<Director,Long> {
}
