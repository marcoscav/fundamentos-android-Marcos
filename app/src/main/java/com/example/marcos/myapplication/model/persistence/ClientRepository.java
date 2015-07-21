package com.example.marcos.myapplication.model.persistence;

import com.example.marcos.myapplication.model.entities.Client;

import java.util.List;

/**
 * Created by Marcos on 21/07/2015.
 */
public interface ClientRepository {

    public abstract void save(Client client);
    public abstract List<Client> getAll();
    public abstract void delete(Client client);

}
