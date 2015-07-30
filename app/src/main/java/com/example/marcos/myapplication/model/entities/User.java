package com.example.marcos.myapplication.model.entities;

import com.example.marcos.myapplication.model.persistence.SQLiteUserRepository;

/**
 * Created by Marcos on 30/07/2015.
 */
public class User {
    private Integer id;
    private String userName;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean authenticate(User user) {
        return SQLiteUserRepository.getInstance().authenticate(user) != null;
    }

}
