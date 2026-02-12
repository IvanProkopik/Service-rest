package org.example.entity;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Value
public class Book {
     Long id;
     String bookTitle;
     Integer yearOfPublication;
     String genre;
     Long authorId;
}
