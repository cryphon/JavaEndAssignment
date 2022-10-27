package com.example.javaend.Models;

import java.io.Serializable;
import java.time.LocalDate;

public class ItemSerialized implements Serializable {
    protected int _id;
    protected String _title;
    protected String _author;
    protected Availability _availability;
    protected LocalDate _lendingDate;
    protected int _lentByUid;

    public ItemSerialized(int _id, String _title, String _author, Availability _availability, LocalDate _lendingDate, int _lentByUid){
        this._id = _id;
        this._title = _title;
        this._author = _author;
        this._availability = _availability;
        this._lendingDate = _lendingDate;
        this._lentByUid = _lentByUid;
    }

}
