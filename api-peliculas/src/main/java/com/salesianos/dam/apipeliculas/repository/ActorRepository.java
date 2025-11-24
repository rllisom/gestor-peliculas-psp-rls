package com.salesianos.dam.apipeliculas.repository;

import com.salesianos.dam.apipeliculas.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor,Long> {
}
