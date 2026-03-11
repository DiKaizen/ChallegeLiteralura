package com.franco.literalura.repository;

import com.franco.literalura.modelos.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    Optional<Autor> findByNombre(String nombre);

}
