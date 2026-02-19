package org.example.dto;

import java.math.BigDecimal;

public record AuthorDto(
        Long id,
        String firstName,
        String lastName,
        BigDecimal phone,
        String gmail
        ) {
}
