package com.example.ramenddang.ramen.dto;

import com.example.ramenddang.ramen.entity.RamenPhoto;
import jakarta.persistence.Column;

import java.util.List;

public record RamenDTO(
        String ramenName,
        String ramenMenu,

        String ramenContent,
        String ramenState,
        String ramenCity,
        String ramenAddress,
        List<RamenPhoto> ramenPhotos
) {
}
