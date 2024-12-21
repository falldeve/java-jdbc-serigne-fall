package com.bbwism.services;

import com.bbwism.repositories.ClientRepo;
import com.bbwism.Entites.Client;
import java.util.List;
import com.bbwism.services.impl.ClientServiceImpl;
public class ClientCreateImpl implements ClientServiceImpl {
    private ClientRepo clientRepo;

    // public ClientCreateImpl() {
    //     this.clientRepo = new ClientRepo(); 
        // }
@Override
    public void create(Client client) {
        clientRepo.insert(client); 
    }
@Override
    public List<Client> findAll() {
        return clientRepo.findAll(); 
    }
@Override
    public Client search(String tel) {
        return clientRepo.selectByTel(tel); 
    }

    // public void close() {
    //     clientRepo.closeConnection(); 
    // }
}