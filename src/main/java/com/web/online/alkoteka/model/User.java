package com.web.online.alkoteka.model;

import com.web.online.alkoteka.model.enumerations.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "repeatedPassword")
    private String repeatedPassword;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "role")
    private Role role;


    public User(String username,String password, String repeatedPassword, String name, String surname, Role role) {
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }
}
