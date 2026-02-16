package org.example.entity;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Author {
     Long id;
     String firstName;
     String lastName;
     BigDecimal phone;
     String gmail;
}
