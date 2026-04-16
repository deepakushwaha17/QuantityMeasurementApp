package com.quantity.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurement_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    // OPTIONAL (for display/logging)
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String operation;      // CONVERT | COMPARE | ADD | SUBTRACT | DIVIDE

    @Column(nullable = false)
    private String measurementType; // LENGTH | WEIGHT | VOLUME | TEMPERATURE

    @Column(nullable = false)
    private Double inputValue;

    @Column(nullable = false)
    private String inputUnit;

    private Double secondValue;
    private String secondUnit;
    private String targetUnit;

    @Column(nullable = false)
    private Double result;

    @Column(nullable = false)
    private String resultUnit;

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}