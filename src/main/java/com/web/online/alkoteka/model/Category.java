package com.web.online.alkoteka.model;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "Category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(length = 4000, name = "description")
    private String description;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
