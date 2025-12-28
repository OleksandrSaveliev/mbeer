package com.tmdna.mbeer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", nullable = false, updatable = false)
    private UUID id;

    @Version
    private Integer version;

    @NotEmpty
    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    private String beerName;

    @NotEmpty
    @NotNull
    private String beerStyle;

    @NotEmpty
    @NotNull
    @Size(max = 50)
    private String upd;

    private Integer quantityOnHand;

    @NotNull
    @Positive
    private BigDecimal price;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
