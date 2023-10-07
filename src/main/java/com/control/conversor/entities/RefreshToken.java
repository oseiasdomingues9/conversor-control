package com.control.conversor.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refresh_token")
@EqualsAndHashCode(of = "id")
public class RefreshToken {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(nullable = false,unique = true)
        private String id;

        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;

        @Column(nullable = false, unique = true)
        private String token;

        @Column(nullable = false)
        private Instant expiryDate;

}
