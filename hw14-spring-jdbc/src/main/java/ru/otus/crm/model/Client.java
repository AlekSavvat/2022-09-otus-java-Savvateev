package ru.otus.crm.model;

import jakarta.annotation.Nonnull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Table("client")
public class Client {
    @Id
    private Long id;

    @Nonnull
    private String name;

    @Nonnull
    @MappedCollection(idColumn = "client_id")
    private Address address;

    @Nonnull
    @MappedCollection(idColumn = "client_id")
    private Set<Phone> phones = new HashSet<>();

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @PersistenceCreator
    public Client(Long id, String name, Address address
            , Set<Phone> phones
    ){
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public Client() {
    }

    public Set<Phone> getPhones() {
        return this.phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Client;
    }

}
