package com.example.javaend.Models;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ItemDatabase {
    protected List<Item> _items;

    //BEGIN getters
    public List<Item> get_items() { return _items; }
    //END getters

    //ctor
    public ItemDatabase() { initDB(); }

    //BEGIN methods
    private void initDB() {
        _items = new ArrayList<>();
        load();
    }

    public void update(){
        try {
            writeAllToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*load and write*/
    private void load(){
        _items = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(new File("items.dat")))) {
            while (true) {
                try {
                    SerializedItem is = (SerializedItem) ois.readObject();
                    _items.add(new Item(is._id, is._title, is._author, is._availability, is._lendingDate, is._lentByUid));
                } catch (EOFException eof) {
                    break; // break out of the loop when at end of file
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException fnf) {
            System.out.print("file not found load: load");
        }
    }

    private void writeAllToFile() throws IOException {
        emptyFile();
        try{
            FileOutputStream fos = new FileOutputStream(new File("items.dat"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(Item i : _items){
                SerializedItem si = new SerializedItem(i.get_id(), i.get_title(), i.get_author(), i.get_availability(), i.get_lendingDate(), i.get_lentByUid());
                oos.writeObject(si);
            }

        } catch (FileNotFoundException fnf) {
            System.out.println("file not found: writeAllToFile");
        } catch (NotSerializableException ns) {
            System.out.println("not serializable");
        }catch (IOException io){
            System.out.println("io exception: writeAllToFile");
        }
    }

    private static void emptyFile() {
        //empty file for overwrite
        try {
            OutputStream os;
            os = Files.newOutputStream(Path.of("items.dat"));
            os.close();
        } catch (IOException io) {
            System.out.println("could not clear file: empty");
        }
    }

    /*item methods*/

    private int getNewItemId() {
        int id = 0;
        for (Item i : _items) {
            if(i.get_id() > id)
                id = i.get_id();
        }
        return id + 1;
    }

    public void addNewItem(String title, String author) {
        _items.add(new Item(getNewItemId(), title, author));
        update();
    }

    public void deleteItem(Item i){
        _items.removeIf(item -> Objects.equals(i.get_id(), item.get_id()));
        update();
    }

    public void lendItem(Item i, User u) {
        i.set_availability(Availability.no);
        i.set_lendingDate(LocalDate.now());
        i.set_lentByUid(u.get_id());
        update();
    }

    public void receiveItem(Item i){
        i.set_availability(Availability.yes);
        //set local date to now, will not be checked. in on lend will be overwritten
        i.set_lendingDate(LocalDate.now());
        i.set_lentByUid(0);
        update();
    }

    public Item getItemById(int id){
        Optional<Item> item = _items.stream().filter(i -> Objects.equals(i.get_id(), id)).findFirst();
        return item.orElse(null);
    }

    //END methods

}
