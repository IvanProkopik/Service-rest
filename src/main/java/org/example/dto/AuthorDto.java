package org.example.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Getter
@Setter
@EqualsAndHashCode
public class AuthorDto{
        Long id;
        String firstName;
        String lastName;
        BigDecimal phone;
        String gmail;

}
