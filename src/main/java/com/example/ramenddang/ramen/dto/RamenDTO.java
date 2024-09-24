package com.example.ramenddang.ramen.dto;

import jakarta.persistence.Column;

public record RamenDTO(
        String ramenName,
        String ramenMenu,

        String ramenContent,
        String ramenState,
        String ramenCity,
        String ramenAddress
) {
}
