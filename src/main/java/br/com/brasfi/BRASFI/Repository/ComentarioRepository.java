package br.com.brasfi.BRASFI.Repository;

import br.com.brasfi.BRASFI.Model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
