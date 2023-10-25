package com.example.demo.user;

import com.example.demo.post.Post;
import com.example.demo.validation.Tckn;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Getter
@Setter
@Where(clause = "deleted = false")
@Table(name = "app_user")
public class User {

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

    @OneToMany(mappedBy = "owner")
    private List<Post> posts;

    public User() {
    }

    public User(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

}
