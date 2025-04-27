package br.com.brasfi.BRASFI.Repository;


import java.util.Optional;

import br.com.brasfi.BRASFI.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long>{



    Optional<MyUser> findByUsername(String username);

}