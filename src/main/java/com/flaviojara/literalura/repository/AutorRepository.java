package com.flaviojara.literalura.repository;

import com.flaviojara.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreIgnoreCase(String nombre);
    List<Autor> findByFechaNacimientoLessThanEqualAndFechaMuerteGreaterThanEqual(Integer birthYear, Integer deathYear);
    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :year AND (a.fechaMuerte >= :year OR a.fechaMuerte IS NULL)")
    List<Autor> findAliveInYear(@Param("year") int year);
}
