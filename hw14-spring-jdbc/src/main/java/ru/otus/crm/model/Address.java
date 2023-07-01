package ru.otus.crm.model;

import jakarta.annotation.Nonnull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("address")
public class Address {
    @Id
    private final Long id;

    @Nonnull
    private final String street;

    @Nonnull
    private final Long clientId;

}
