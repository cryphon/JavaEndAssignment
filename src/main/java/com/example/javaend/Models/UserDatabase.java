package com.example.javaend.Models;

import java.time.LocalDate;
import java.util.*;

public class UserDatabase {

    protected List<User> _users;

    //BEGIN getters

    public List<User> getUsers(){ return _users; }
    //END getters

    //ctor
    public UserDatabase()
    {
        initDB();
    }

    //BEGIN methods
    public void initDB()
    {
        _users = new ArrayList<>();
        _users.add(new User(getNewUserID(), "root", "root", "root", LocalDate.of(2001, 9, 11)));
        _users.add(new User(getNewUserID(), "admin", "admin", "root", LocalDate.of(2002, 5, 15)));
    }

    public int getNewUserID()
    {
        int id = 0;
        for (User u : _users) {
            if(u.getId() > id)
                id = u.getId();
        }
        return id;
    }

    public boolean VerifyCredentials(String name, String passwd)
    {
        User unverifiedUser;
        try{
            unverifiedUser = getUserByName(name);
            assert unverifiedUser != null;
            return _users.stream().anyMatch(u -> Objects.equals(unverifiedUser.getFirstName(), name) && Objects.equals(unverifiedUser.getPasswd(), passwd));
        }catch(Exception e){ return false; }
    }

    public User getUserByName(String name){
        Optional<User> user = _users.stream().filter(u -> Objects.equals(u.getFirstName(), name)).findFirst();
        if(user.isEmpty())
            return null;
        return (User) user.get();
    }
    //END methods
}
