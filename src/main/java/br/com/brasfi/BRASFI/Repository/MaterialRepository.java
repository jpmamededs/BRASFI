package br.com.brasfi.BRASFI.Repository;

import br.com.brasfi.BRASFI.Model.MaterialApoio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<MaterialApoio,Long> {
}
