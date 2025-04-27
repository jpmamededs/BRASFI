package br.com.brasfi.BRASFI.Service;

import java.util.Optional;
import br.com.brasfi.BRASFI.Repository.UserRepository;
import br.com.brasfi.BRASFI.Model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

//    @Autowired
//    private UserRepository repository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//    Optional<User> user = repository.findByUsername(username);
//    if (user.isPresent()) {
//        var userObj = user.get();
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(userObj.getUsername())
//                .password(userObj.getPassword())
//                .build();
//    }else{
//        throw new UsernameNotFoundException(username);
//    }
//    }



}