package com.salesianos.dam.apipeliculas.model;

import com.salesianos.dam.apipeliculas.error.DirectorMenorEdadException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
@Entity
public class Director {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private int anioNacimiento;

    @OneToMany(mappedBy = "director")
    @Builder.Default
    private Set<Pelicula> peliculas = new HashSet<>();


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Director director = (Director) o;
        return getId() != null && Objects.equals(getId(), director.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    //Helpers

    public static void comprobarEdad(int anioNacimiento){
        LocalDate fechaActual = LocalDate.now();
        int tope = 18;
        if((fechaActual.getYear()-anioNacimiento)<tope) throw new DirectorMenorEdadException("El director no puede ser menor de edad");
    }

}
