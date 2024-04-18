package Tugas3;

import java.util.ArrayList;
import java.util.List;

class Item {
    private int id;
    private String name;
    private int nik;
    private String dateOfBirth;
    private String address;

    public Item(int id, String name, int nik, String dateOfBirth, String address) {
        this.id = id;
        this.name = name;
        this.nik = nik;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNik() {
        return nik;
    }

    public void setNik(int nik) {
        this.nik = nik;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

public class ItemStore {
    private List<Item> items;

    public ItemStore() {
      this.items = new ArrayList<>();
    }

    public List<Item> getAll() {
      return this.items;
    }
    
    public void addItem(Item item) {
        items.add(item);
    }

    public Item getItemById(int id) {
      for (Item item : items) {
        if (item.getId() == id) {
          return item;
        }
      }
      return null;
    }
    
    public boolean isNIKExist(int nik){
      for (Item item : items) {
        if (item.getNik() == nik) {
          return true;
        }
      }

      return false;
    }

    public void updateItem(int id, String newName, int newNik, String newDateOfBirth, String newAddress) {
        for (Item item : items) {
            if (item.getId() == id) {
                item.setName(newName);
                item.setNik(newNik);
                item.setDateOfBirth(newDateOfBirth);
                item.setAddress(newAddress);
                return;
            }
        }
    }

    public void deleteItem(int id) {
        Item itemToRemove = null;
        for (Item item : items) {
            if (item.getId() == id) {
                itemToRemove = item;
                break;
            }
        }
        if (itemToRemove != null) {
            items.remove(itemToRemove);
        }
    }

    public Item[] getItems() {
      throw new UnsupportedOperationException("Unimplemented method 'getItems'");
    }
}
