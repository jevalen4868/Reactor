package com.j01.reactor.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Data
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
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

    public UserDetails toUserDetails() {
        User u = this;
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return u.getAuthorities();
            }

            @Override
            public String getPassword() {
                return u.getPassword();
            }

            @Override
            public String getUsername() {
                return u.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return u.isAccountNonExpired();
            }

            @Override
            public boolean isAccountNonLocked() {
                return u.isAccountNonLocked();
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return u.isCredentialsNonExpired();
            }

            @Override
            public boolean isEnabled() {
                return u.isEnabled();
            }
        };
    }
}
