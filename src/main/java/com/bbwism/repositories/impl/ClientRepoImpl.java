package com.bbwism.repositories.impl;

import com.bbwism.Entites.Client;
import com.bbwism.repositories.bd.core.Repository;

public interface ClientRepoImpl extends Repository<Client> {
    Client selectByTel(String tel);
}
