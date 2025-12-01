package com.tmdna.mbeer.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar", nullable = false, updatable = false)
    private UUID id;
    private String customerName;

    @Version
    private Integer version;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
