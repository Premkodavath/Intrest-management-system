package com.loan.controller;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.loan.entity.Client;
import com.loan.service.ClientService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5500") // Allows frontend to access API
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping
    public Client saveClient(@RequestBody Client client) {
        // Ensure client has non-null dates
        if (client.getDateTaken() == null || client.getDatePaid() == null) {
            throw new IllegalArgumentException("Both dateTaken and datePaid must be provided");
        }
        
        // Save client
        return clientService.saveClient(client);
    }

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }
}
