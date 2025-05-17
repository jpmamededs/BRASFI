package br.com.brasfi.BRASFI.Repository;

import br.com.brasfi.BRASFI.Model.Curso;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {


    @EntityGraph(attributePaths = {"modulos"})
    Optional<Curso> findWithModulosById(Long id);

    @EntityGraph(attributePaths = {"modulos.aulas"})
    Optional<Curso> findWithModulosAndAulasById(Long id);

    @EntityGraph(attributePaths = {"modulos.aulas.materiais"})
    Optional<Curso> findCompleteById(Long id);

}