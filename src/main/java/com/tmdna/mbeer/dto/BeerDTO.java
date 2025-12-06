package com.tmdna.mbeer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BeerDTO {
    private UUID id;
    private Integer version;

    @NotEmpty
    @NotNull
    private String beerName;

    @NotEmpty
    @NotNull
    private String beerStyle;

    @NotEmpty
    @NotNull
    private String upd;

    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
