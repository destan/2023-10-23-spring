package com.example.demo.user;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.post.Post;
import com.example.demo.validation.Tckn;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Where(clause = "deleted = false")
@Table(name = "app_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Tckn
    @Column(nullable = true, unique = false)
    private String tckn;

    @NotBlank
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private boolean deleted;

    @OneToOne
    private Address address;

    @OneToMany(mappedBy = "owner") // Not a good solution: , fetch = FetchType.EAGER
    private List<Post> posts;

    public User() {
    }

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public User(DecodedJWT decoded) {
        this.id = decoded.getClaim("uid").asLong();
        this.email = decoded.getSubject();
        //TODO get roles from JWT
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getUsername() {
        return email;
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
