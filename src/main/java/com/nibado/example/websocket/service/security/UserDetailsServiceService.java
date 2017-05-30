package com.nibado.example.websocket.service.security;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if (username.equals("kowalski")) {
//            throw new UsernameNotFoundException("Nie znam cię !");
//        }
        return new User(username, "password", new ArrayList<GrantedAuthority>());
    }

}
