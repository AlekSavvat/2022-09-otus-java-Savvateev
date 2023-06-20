package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
@ToString
public class Client implements Cloneable {
    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "client")
    @Setter(AccessLevel.NONE)
    private List<Phone> phones;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;

        if (phones != null) {
            this.phones = new ArrayList<Phone>();
            this.phones.addAll(phones);
            phones.forEach(phone -> phone.setClient(this));
        }
    }

    public void setPhones(List<Phone> phones) {
        if(phones == null) {
            if (!this.phones.isEmpty()){
                this.phones.clear();
            }
        }
        else {
            this.phones = new ArrayList<Phone>();
            this.phones.addAll(phones);
            phones.forEach(phone -> phone.setClient(this));
        }
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phones);
    }

//    @Override
//    public String toString() {
//        return "Client{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                '}';
//    }
}
