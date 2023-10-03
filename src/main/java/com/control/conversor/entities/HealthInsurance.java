package com.control.conversor.entities;

import com.control.conversor.enums.PlanType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "health_insurance")
@EqualsAndHashCode(of = "id")
public class HealthInsurance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false,unique = true)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @ElementCollection
    @CollectionTable(name = "tiss_versions", joinColumns = @JoinColumn(name = "health_plan_id"))
    private List<String> version;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlanType planType;

}
