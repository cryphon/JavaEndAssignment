package com.example.javaend.Models;

import java.time.LocalDate;

public class Item {
    protected int _id;
    protected String _title;
    protected String _author;
    protected Availability _availability;
    protected LocalDate _lendingDate;
    protected int _lentByUid;


    //BEGIN getters/setters
    public int get_id(){ return _id; }
    public String get_title() { return _title; }
    public String get_author() { return _author; }
    public Availability get_availability() { return _availability; }
    public LocalDate get_lendingDate() { return _lendingDate; }
    public int get_lentByUid() { return _lentByUid; }
    public void set_title(String title) { _title = title; }
    public void set_author(String author) { _author = author; }
    public void set_availability(Availability a) { _availability = a; }
    public void set_lentByUid(int uid) { _lentByUid = uid; }
    public void set_lendingDate(LocalDate date) {_lendingDate = date; }
    //END getters/setters

    //ctor
    public Item(int id, String title, String author)
    {
        this._id = id;
        this._title = title;
        this._author = author;
        this._availability = Availability.yes;
    }
    public Item(int _id, String _title, String _author, Availability _availability, LocalDate _lendingDate, int _lentByUid)
    {
        this._id = _id;
        this._title = _title;
        this._author = _author;
        this._availability = _availability;
        this._lendingDate = _lendingDate;
        this._lentByUid = _lentByUid;
    }
}
