package com.bbwism.services.impl;

import java.util.List;

import com.bbwism.Entites.Client;

public interface ClientServiceImpl {
    void create(Client client);
    List<Client> findAll();
    Client search(String tel);
}
