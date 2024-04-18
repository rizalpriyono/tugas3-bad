package Tugas3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ItemListGUI extends JFrame {
  private JTable table;
  private DefaultTableModel model;
  private ItemStore itemStore;

  public ItemListGUI(ItemStore itemStore) {
    this.itemStore = itemStore;
    setTitle("Item List");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    initComponents();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void initComponents() {
    // Membuat tabel dengan model default
    model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tidak mengizinkan pengeditan sel di tabel
            }
        };
    table = new JTable(model);

    // Menambahkan kolom ke model tabel
    model.addColumn("No");
    model.addColumn("ID");
    model.addColumn("Name");
    model.addColumn("NIK");
    model.addColumn("Date of Birth");
    model.addColumn("Address");

    // Mengisi tabel dengan data dari ItemStore
for (int i = 0; i < itemStore.getAll().size(); i++) {
            Item item = itemStore.getAll().get(i);
            Object[] row = {i + 1, item.getId(), item.getName(), item.getNik(), item.getDateOfBirth(), item.getAddress()};
            model.addRow(row);
        }

    JButton addButton = new JButton("Tambah Data");
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        tambahItem();
      }
    });

    JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }
        });

    JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });

    // Mengatur layout menggunakan BorderLayout
    setLayout(new BorderLayout());

    // Menambahkan tabel ke dalam JScrollPane agar bisa di-scroll
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addButton);
    buttonPanel.add(updateButton);
    buttonPanel.add(deleteButton);
    add(buttonPanel, BorderLayout.SOUTH);
  }
  
  private void tambahItem() {
    // Tampilkan dialog untuk memasukkan detail item baru
    JTextField idField = new JTextField(5);
    JTextField nameField = new JTextField(20);
    JTextField nikField = new JTextField(15);
    JTextField dobField = new JTextField(20);
    JTextField addressField = new JTextField(20);

    JPanel myPanel = new JPanel();
    myPanel.setLayout(new GridLayout(5, 2));
    myPanel.add(new JLabel("ID:"));
    myPanel.add(idField);
    myPanel.add(new JLabel("Name:"));
    myPanel.add(nameField);
    myPanel.add(new JLabel("NIK:"));
    myPanel.add(nikField);
    myPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
    myPanel.add(dobField);
    myPanel.add(new JLabel("Address:"));
    myPanel.add(addressField);

    int result = JOptionPane.showConfirmDialog(null, myPanel,
        "Masukkan Detail Item Baru", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      // Validasi input
      try {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        int nik = Integer.parseInt(nikField.getText());

        if (itemStore.isNIKExist(nik)) {
          JOptionPane.showMessageDialog(null, "NIK sudah ada.", "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        String dob = dobField.getText();
        String address = addressField.getText();

        // Tambahkan item baru ke dalam ItemStore
        Item newItem = new Item(id, name, nik, dob, address);
        itemStore.addItem(newItem);

        // Tambahkan data baru ke dalam tabel
        Object[] row = { table.getRowCount() + 1, newItem.getId(), newItem.getName(), newItem.getNik(),
            newItem.getDateOfBirth(), newItem.getAddress() };
        model.addRow(row);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "ID harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
    
  private void updateItem() {
    // Dapatkan indeks baris yang dipilih
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
      JOptionPane.showMessageDialog(null, "Pilih baris untuk update.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    // Ambil data item dari baris yang dipilih
    int id = (int) table.getValueAt(selectedRow, 1);
    String name = (String) table.getValueAt(selectedRow, 2);
    String nik = (String) table.getValueAt(selectedRow, 3).toString();
    String dob = (String) table.getValueAt(selectedRow, 4);
    String address = (String) table.getValueAt(selectedRow, 5);

    // Tampilkan dialog untuk update data item

    JTextField nameField = new JTextField(name, 20);
    JTextField nikField = new JTextField(nik, 15);
    JTextField dobField = new JTextField(dob, 20);
    JTextField addressField = new JTextField(address, 20);

    JPanel myPanel = new JPanel();
    myPanel.setLayout(new GridLayout(4, 2));
    myPanel.add(new JLabel("Name:"));
    myPanel.add(nameField);
    myPanel.add(new JLabel("NIK:"));
    myPanel.add(nikField);
    myPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
    myPanel.add(dobField);
    myPanel.add(new JLabel("Address:"));
    myPanel.add(addressField);

    int result = JOptionPane.showConfirmDialog(null, myPanel,
        "Update Data Item", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      try {
        String newName = nameField.getText();
        int newNik = Integer.parseInt(nikField.getText());
        String newAddress = addressField.getText();

        // Update data item dalam ItemStore
        itemStore.updateItem(id, newName, newNik, dob, newAddress);

        // Update data dalam tabel
        table.setValueAt(newName, selectedRow, 2);
        table.setValueAt(newNik, selectedRow, 3);
        table.setValueAt(newAddress, selectedRow, 5);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "ID harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
    
  private void deleteItem() {
    // Dapatkan indeks baris yang dipilih
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
      JOptionPane.showMessageDialog(null, "Pilih baris untuk hapus.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    int confirmResult = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus item ini?", "Konfirmasi Hapus",
        JOptionPane.YES_NO_OPTION);
    if (confirmResult == JOptionPane.YES_OPTION) {
      // Hapus data dari ItemStore
      int id = (int) table.getValueAt(selectedRow, 1); // ID berada di kolom kedua
      itemStore.deleteItem(id);

      // Hapus baris dari tabel
      model.removeRow(selectedRow);

      // Update nomor urut di kolom "NO"
      updateRowNumbers();
    }
  }

  private void updateRowNumbers() {
    for (int i = 0; i < model.getRowCount(); i++) {
      model.setValueAt(i + 1, i, 0); // Mengatur nomor urut sesuai dengan indeks baris
    }
  }
    
}
