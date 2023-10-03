package com.control.panel.conversor.entities;

import com.control.panel.conversor.enums.PlanType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "health_plan")
@EqualsAndHashCode(of = "id")
public class HealthPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false,unique = true)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @ElementCollection
    @CollectionTable(name = "plan_version", joinColumns = @JoinColumn(name = "version_id"))
    @Column(name = "version")
    private List<String> version;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlanType planType;

}
