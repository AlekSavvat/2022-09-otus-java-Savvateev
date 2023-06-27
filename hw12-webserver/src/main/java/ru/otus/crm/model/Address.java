package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
@ToString
public class Address {
    @Id
    @SequenceGenerator(name = "address_gen", sequenceName = "address_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;
}
