package com.example.ramenddang.ramen.dto;

public record UpdateRamenDTO(
        String ramenName,
        String ramenMenu,

        String ramenContent,
        String ramenState,
        String ramenCity,
        String ramenAddress
) {

}
