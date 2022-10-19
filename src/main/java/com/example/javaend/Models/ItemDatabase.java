package com.example.javaend.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class ItemDatabase {
    protected List<Item> _items;

    //BEGIN getters
    public List<Item> get_items() { return _items; }
    //END getters

    //ctor
    private ItemDatabase() { initDB(); }


    //TEMP singleton
    private static class singletonHolder {
        private static final ItemDatabase INSTANCE = new ItemDatabase();
    }

    //get instance
    public static ItemDatabase getInstance(){
        return singletonHolder.INSTANCE;
    }

    //TEMP singleton


    //BEGIN methods
    private void initDB(){
        _items = new ArrayList<>();
        addNewItem("Java for Dummies, 13th Edition", "E. De Vries");
        addNewItem("Java for Dummies, 13th Edition", "E. De Vries");
        addNewItem("Java for Dummies, 13th Edition", "E. De Vries");
        addNewItem("Java for Dummies, 13th Edition", "E. De Vries");
        addNewItem("Java for Dummies, 13th Edition", "E. De Vries");
        addNewItem("Java for Dummies, 13th Edition", "E. De Vries");
    }

    //END methods

    private int getNewItemId()
    {
        int id = 0;
        for (Item i : _items) {
            if(i.get_id() > id)
                id = i.get_id();
        }
        return id + 1;
    }

    public void addNewItem(String title, String author)  { _items.add(new Item(getNewItemId(), title, author)); }

    public void changeItemAvailability(Item i, Availability a, User u){ i.set_availability(a); i.set_lentBy(u);}

    public Item getItemById(int id){
        Optional<Item> item = _items.stream().filter(i -> Objects.equals(i.get_id(), id)).findFirst();
        return item.orElse(null);
    }

}
