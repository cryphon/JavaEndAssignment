package com.example.javaend.Models;

import java.time.LocalDateTime;

public class Item {
    protected int _id;
    protected String _title;
    protected String _author;
    protected Availability _availability;
    protected LocalDateTime _lendingDate;
    protected User _lentBy;


    //BEGIN getters/setters
    public int get_id(){ return _id; }
    public String get_title() { return _title; }
    public String get_author() { return _author; }
    public Availability get_availability() { return _availability; }
    public LocalDateTime get_lendingDate(){ return _lendingDate; }
    public User get_lentBy() { return _lentBy; }
    public void set_availability(Availability a) { _availability = a; }
    public void set_lentBy(User u) { _lentBy = u; }
    //END getters/setters

    //ctor
    public Item(int id, String title, String author){
        this._id = id;
        this._title = title;
        this._author = author;
        this._availability = Availability.yes;
    }
}
