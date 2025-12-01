package com.tmdna.mbeer.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Beer {
    private UUID id;
    private Integer version;
    private String beerName;
    private String beerStyle;
    private String upd;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
