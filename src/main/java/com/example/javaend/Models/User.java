package com.example.javaend.Models;

import java.time.LocalDate;

public class User {
    private final int _id;
    private final String _firstName;
    private final String _lastName;
    private final String _passwd;
    private final LocalDate _dob;

    //BEGIN getters
    public int get_id() { return _id; }
    public String get_firstName(){ return _firstName; }
    public String get_lastName() { return _lastName; }

    public String get_fullName(){ return String.format("%s %s", get_firstName(), get_lastName()); }
    public String get_passwd() { return _passwd; }
    public LocalDate get_dateOfBirth() { return _dob; }
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
