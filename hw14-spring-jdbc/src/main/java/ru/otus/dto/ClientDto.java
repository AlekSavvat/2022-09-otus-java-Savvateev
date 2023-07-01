package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Long id;

    private String name;

    private String address;

    private String phones;

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.address = client.getAddress() == null ? null : client.getAddress().getStreet();
        this.phones = client.getPhones().stream().map(Phone::getNumber).collect(Collectors.joining("; "));
    }

    public Client toClient(){
        Set<Phone> phoneList = Arrays.stream(this.getPhones().split(";"))
                .map(s -> new Phone(null, s, this.getId()))
                .collect(Collectors.toSet());

        return  new Client(this.getId(), this.getName()
                , new Address(null, this.getAddress(), this.getId())
                , phoneList);
    }
}
