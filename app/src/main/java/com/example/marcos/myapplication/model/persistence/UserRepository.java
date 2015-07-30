package com.example.marcos.myapplication.model.persistence;

import com.example.marcos.myapplication.model.entities.User;

/**
 * Created by Marcos on 30/07/2015.
 */
public interface UserRepository {
    public abstract User authenticate(User user);
}
