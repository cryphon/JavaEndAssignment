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
        addNewUser( "root", "root", "root", LocalDate.of(2001, 9, 11));
        addNewUser( "admin", "admin", "root", LocalDate.of(2002, 5, 15));
        addNewUser("Emma", "Smith", "root", LocalDate.now());
    }

    public int getNewUserID()
    {
        int id = 0;
        for (User u : _users) {
            if(u.get_id() > id)
                id = u.get_id();
        }
        return id + 1;
    }

    public boolean VerifyCredentials(String name, String passwd)
    {
        User unverifiedUser;
        try{
            unverifiedUser = getUserByName(name);
            assert unverifiedUser != null;
            return _users.stream().anyMatch(u -> Objects.equals(unverifiedUser.get_firstName(), name) && Objects.equals(unverifiedUser.get_passwd(), passwd));
        }catch(Exception e){ return false; }
    }

    public User getUserByName(String name){
        Optional<User> user = _users.stream().filter(u -> Objects.equals(u.get_firstName(), name)).findFirst();
        if(user.isEmpty())
            return null;
        return user.get();
    }

    public User getUserById(int id){
        Optional<User> user = _users.stream().filter(u -> Objects.equals(u.get_id(), id)).findFirst();
        if(user.isEmpty())
            return null;
        return user.get();
    }

    public void addNewUser(String fName, String lName, String passwd ,LocalDate dob) { _users.add(new User(getNewUserID(), fName, lName, passwd, dob)); }
    //END methods
}
