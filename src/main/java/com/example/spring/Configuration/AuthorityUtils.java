package com.example.spring.Configuration;

import com.example.spring.model.User;
import com.example.spring.model.enums.Privilege;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AuthorityUtils {
    public static Collection<? extends GrantedAuthority> createAuthorities(Set<Privilege> privileges) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(Privilege privilege : privileges)
        {
            authorities.add(() ->privilege.name());
        }
        return authorities;
    }

}
