package br.com.brasfi.BRASFI.Repository;

import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Model.enums.TipoPostagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem,Long> {


    List<Postagem> findAllByTag(TipoPostagem tag);

    List<Postagem> findAllByFixadoTrue();

    Optional<Postagem> findById(Long id);

    @Modifying
    @Query("UPDATE Postagem p SET p.autor = :#{#novaPostagem.autor}, " +
            "p.tag = :#{#novaPostagem.tag}, " +
            "p.titulo = :#{#novaPostagem.titulo}, " +
            "p.paragrafo = :#{#novaPostagem.paragrafo}, " +
            "p.imagemOuVideo = :#{#novaPostagem.imagemOuVideo}, " +
            "p.link = :#{#novaPostagem.link}, " +
            "p.fixado = :#{#novaPostagem.fixado} " +
            "WHERE p.id = :id")
    void atualizarPostagem(@Param("id") Long id, @Param("novaPostagem") Postagem novaPostagem);


    void deleteById(Long id);


    @Modifying
    @Query("UPDATE Postagem p SET p.fixado = :fixado WHERE p.id = :id")
    void atualizarFixado(@Param("id") Long id, @Param("fixado") boolean fixado);

}
