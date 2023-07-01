package ru.otus.crm.model;

import jakarta.annotation.Nonnull;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("phone")
@AllArgsConstructor
public class Phone {
    @Id
    private final Long id;

    @Nonnull
    private final String number;

    @Nonnull
    private final Long clientId;

}
