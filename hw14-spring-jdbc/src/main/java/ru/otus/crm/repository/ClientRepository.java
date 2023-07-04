package ru.otus.crm.repository;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import ru.otus.crm.model.Client;

//import java.util.Optional;
import java.util.List;


public interface ClientRepository extends ListCrudRepository<Client, Long> {

    List<Client> findAll();

}
