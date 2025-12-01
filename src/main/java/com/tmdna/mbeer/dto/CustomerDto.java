package com.tmdna.mbeer.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDto {
    private UUID id;
    private String customerName;
    private Integer version;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
