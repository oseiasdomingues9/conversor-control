package com.control.panel.conversor.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
@EqualsAndHashCode(of = "id")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false,unique = true)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String key;

//    @OneToMany(mappedBy = "client")
//    private List<User> user;
}
