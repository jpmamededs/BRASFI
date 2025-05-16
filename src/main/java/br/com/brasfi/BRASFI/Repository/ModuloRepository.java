package br.com.brasfi.BRASFI.Repository;

import br.com.brasfi.BRASFI.Model.Modulo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModuloRepository extends JpaRepository<Modulo,Long> {



    @Query("SELECT m FROM Modulo m LEFT JOIN FETCH m.aulas WHERE m.curso.id = :cursoId")
    List<Modulo> findByCursoIdWithAulas(@Param("cursoId") Long cursoId);

    @Query("SELECT m FROM Modulo m LEFT JOIN FETCH m.aulas a LEFT JOIN FETCH a.materiais WHERE m.curso.id = :cursoId")
    List<Modulo> findByCursoIdWithAulasAndMateriais(@Param("cursoId") Long cursoId);



}
