package org.lukaszse.carRental.config;

import lombok.RequiredArgsConstructor;
import org.lukaszse.carRental.enums.SecurityRole;
import org.lukaszse.carRental.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static org.lukaszse.carRental.enums.SecurityRole.*;

@RequiredArgsConstructor
public class MyUserPrincipal implements UserDetails {

    private final User user;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if("ADMIN".equals(user.getUserRole())) {
            return List.of(new SimpleGrantedAuthority(ROLE_ADMIN.getFullName()));
        } else if ("USER".equals(user.getUserRole())) {
            return List.of(new SimpleGrantedAuthority(ROLE_USER.getFullName()));
        } else {
            return List.of(new SimpleGrantedAuthority(ROLE_GUEST.getFullName()));
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
