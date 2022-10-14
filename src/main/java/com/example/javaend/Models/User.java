package com.example.javaend.Models;

import java.time.LocalDate;

public class User {
    protected int _id;
    protected String _firstName;
    protected String _lastName;
    protected String _passwd;
    protected LocalDate _dob;

    //BEGIN getters
    public int getId() { return _id; }
    public String getFirstName(){ return _firstName; }
    public String getLastName() { return _lastName; }
    public String getPasswd() { return _passwd; }
    public LocalDate getDateOfBirth() { return _dob; }
    //END getters


    //ctor
    public User(int id, String fName, String lName, String passwd, LocalDate dob){
        this._id = id;
        this._firstName = fName;
        this._lastName = lName;
        this._passwd = passwd;
        this._dob = dob;
    }
}
