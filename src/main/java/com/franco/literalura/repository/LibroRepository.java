package com.franco.literalura.repository;

import com.franco.literalura.modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTitulo(String titulo);

    @Query("""
       SELECT l FROM Libro l
       JOIN l.autor a
       WHERE a.fechaDeNacimiento <= :anio
       AND (a.fechaDeFallecimiento >= :anio OR a.fechaDeFallecimiento IS NULL)
       """)
    List<Libro> librosDeAutoresVivosEn(@Param("anio") Integer anio);

    List<Libro> findByIdiomasIgnoreCase(String idiomas);
}

