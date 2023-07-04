package ru.otus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.dto.ClientDto;
import ru.otus.exceptions.ResourceNotFoundException;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final DBServiceClient serviceClient;

    @GetMapping
    public String getAllClient(Model model){
        List<ClientDto> listClient = serviceClient.findAll().stream()
                .map(ClientDto::new)
                .sorted(Comparator.comparingLong(client -> client.getId()))
                .toList();
        model.addAttribute("clients", listClient);
        return "clientsList";
    }

    @GetMapping("/create")
    public String clientCreateView(Model model, @RequestParam(name = "id", required = false) Long id) {
        ClientDto client;

        if (id != null) {
            client = new ClientDto(serviceClient.getClient(id).orElseThrow(() ->
                    new ResourceNotFoundException("Not found client with id= " + id)));
        } else {
            client = new ClientDto();
        }

        model.addAttribute("client", client);
        return "client-form";
    }

    @PostMapping("/create")
    public String saveClient(ClientDto client) {
        serviceClient.save(client.toClient());
        return "redirect:/clients";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam(name = "id") Long id) {
        serviceClient.deleteById(id);
        return "redirect:/clients";
    }
}
