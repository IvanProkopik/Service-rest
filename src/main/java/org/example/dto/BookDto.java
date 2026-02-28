package org.example.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@Getter
@Setter
@EqualsAndHashCode
public class BookDto {
    Long id;
    String description;
}
