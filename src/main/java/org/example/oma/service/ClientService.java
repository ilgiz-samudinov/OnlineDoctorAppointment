package org.example.oma.service;

import org.example.oma.model.Client;
import org.example.oma.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> getClientById(long id) {
        return clientRepository.findById(id);
    }


    public Client updateClient(Long id, Client client) {
        Optional<Client> existingClient = getClientById(id);
        if (existingClient.isPresent()) {
            Client updateClient = existingClient.get();
            updateClient.setLastName(client.getLastName());
            updateClient.setFirstName(client.getFirstName());
            updateClient.setMiddleName(client.getMiddleName());
            updateClient.setPhone(client.getPhone());
            return clientRepository.save(updateClient);
        } else {
            return null;
        }

    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }


    public List<Client> getAllClients() {
        return clientRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }


    public Optional<Client> findByPhone(String clientPhone){
        return clientRepository.findByPhone(clientPhone);
    }

}
