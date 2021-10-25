package com.thiendz.wipe.wipeserve.data.model;

import com.thiendz.wipe.wipeserve.utils.enums.UserStatus;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
public class User extends Base implements UserDetails {
    @Column(unique = true)
    String username;
    String password;
    @Column(unique = true)
    String email;
    @Column(unique = true)
    String phoneNumber;
    @Enumerated(EnumType.STRING)
    UserStatus status;

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return false;
    }
}
