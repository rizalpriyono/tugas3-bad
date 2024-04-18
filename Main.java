package Tugas3;

import javax.swing.*;

public class Main {
  
  public static void main(String[] args) {
         ItemStore itemStore = new ItemStore();
        itemStore.addItem(new Item(123, "John Doe", 1234567890, "1990-01-01", "123 Main St"));
        itemStore.addItem(new Item(124, "Jane Smith", 987654321, "1995-05-05", "456 Oak Ave"));

        // Membuat instance dari kelas ItemListGUI
        SwingUtilities.invokeLater(() -> new ItemListGUI(itemStore));
    }
}

