package com.flaviojara.literalura.repository;

import com.flaviojara.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainingIgnoreCase(String nombreLibro);
    List<Libro> findByIdiomasContainingIgnoreCase(String idioma);
}
