package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class XManagerGUI extends JFrame {
    private XManagerImpl manager;
    private JTable table;
    private DefaultTableModel tableModel;

    public XManagerGUI() {
        manager = new XManagerImpl();

        setTitle("X Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Price", "Total", "Destination", "Duration", "Tour Guide"}, 0);
        table = new JTable(tableModel);
        loadTableData();
        JScrollPane tableScrollPane = new JScrollPane(table);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddDialog();
            }
        });
        buttonPanel.add(addButton);

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEditDialog();
            }
        });
        buttonPanel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteDialog();
            }
        });
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(new JLabel("Search:"), BorderLayout.WEST);
        JTextField searchField = new JTextField();
        searchPanel.add(searchField, BorderLayout.CENTER);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTour(searchField.getText());
            }
        });
        searchPanel.add(searchButton, BorderLayout.EAST);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Sort Panel
        JPanel sortPanel = new JPanel(new FlowLayout());
        JButton sortAscButton = new JButton("Sort Ascending");
        sortAscButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortTour(true);
            }
        });
        sortPanel.add(sortAscButton);

        JButton sortDescButton = new JButton("Sort Descending");
        sortDescButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortTour(false);
            }
        });
        sortPanel.add(sortDescButton);

        panel.add(sortPanel, BorderLayout.NORTH);

        add(panel);
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        for (Tour tour : manager.sortedX(0, true)) {
            tableModel.addRow(new Object[]{tour.getProductId(), tour.getProductName(), tour.getProductPrice(), tour.getProductTotal(), tour.getDestination(), tour.getDuration(), tour.getTourGuide()});
        }
    }

    private void showAddDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField totalField = new JTextField();
        JTextField destinationField = new JTextField();
        JTextField durationField = new JTextField();
        JTextField tourGuideField = new JTextField();

        Object[] message = {
                "ID:", idField,
                "Name:", nameField,
                "Price:", priceField,
                "Total:", totalField,
                "Destination:", destinationField,
                "Duration:", durationField,
                "Tour Guide:", tourGuideField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add Tour", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (validateFields(idField, nameField, priceField, totalField, destinationField, durationField, tourGuideField)) {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int total = Integer.parseInt(totalField.getText());
                String destination = destinationField.getText();
                int duration = Integer.parseInt(durationField.getText());
                String tourGuide = tourGuideField.getText();

                Tour tour = new Tour(id, name, price, total, destination, duration, tourGuide);
                manager.addX(tour);
                loadTableData();
            }
        }
    }

    private void showEditDialog() {
        String idString = JOptionPane.showInputDialog(this, "Enter Tour ID to Edit:");
        if (idString != null && !idString.isEmpty()) {
            try {
                int id = Integer.parseInt(idString);
                Tour tour = manager.searchXById(id);
                if (tour != null) {
                    JTextField nameField = new JTextField(tour.getProductName());
                    JTextField priceField = new JTextField(String.valueOf(tour.getProductPrice()));
                    JTextField totalField = new JTextField(String.valueOf(tour.getProductTotal()));
                    JTextField destinationField = new JTextField(tour.getDestination());
                    JTextField durationField = new JTextField(String.valueOf(tour.getDuration()));
                    JTextField tourGuideField = new JTextField(tour.getTourGuide());

                    Object[] message = {
                            "Name:", nameField,
                            "Price:", priceField,
                            "Total:", totalField,
                            "Destination:", destinationField,
                            "Duration:", durationField,
                            "Tour Guide:", tourGuideField
                    };

                    int option = JOptionPane.showConfirmDialog(null, message, "Edit Tour", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        if (validateFields(nameField, priceField, totalField, destinationField, durationField, tourGuideField)) {
                            String name = nameField.getText();
                            double price = Double.parseDouble(priceField.getText());
                            int total = Integer.parseInt(totalField.getText());
                            String destination = destinationField.getText();
                            int duration = Integer.parseInt(durationField.getText());
                            String tourGuide = tourGuideField.getText();

                            Tour updatedTour = new Tour(id, name, price, total, destination, duration, tourGuide);
                            manager.editX(updatedTour);
                            loadTableData();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Tour not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showDeleteDialog() {
        String idString = JOptionPane.showInputDialog(this, "Enter Tour ID to Delete:");
        if (idString != null && !idString.isEmpty()) {
            try {
                int id = Integer.parseInt(idString);
                manager.delX(id);
                loadTableData();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchTour(String name) {
        List<Tour> tours = manager.searchX(name);
        tableModel.setRowCount(0);
        for (Tour tour : tours) {
            tableModel.addRow(new Object[]{tour.getProductId(), tour.getProductName(), tour.getProductPrice(), tour.getProductTotal(), tour.getDestination(), tour.getDuration(), tour.getTourGuide()});
        }
    }

    private void sortTour(boolean ascending) {
        List<Tour> tours = manager.sortedX(0, ascending);
        tableModel.setRowCount(0);
        for (Tour tour : tours) {
            tableModel.addRow(new Object[]{tour.getProductId(), tour.getProductName(), tour.getProductPrice(), tour.getProductTotal(), tour.getDestination(), tour.getDuration(), tour.getTourGuide()});
        }
    }

    private boolean validateFields(JTextField... fields) {
        for (JTextField field : fields) {
            if (field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        try {
            Integer.parseInt(fields[0].getText()); // ID
            Double.parseDouble(fields[2].getText()); // Price
            Integer.parseInt(fields[3].getText()); // Total
            Integer.parseInt(fields[5].getText()); // Duration
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new XManagerGUI().setVisible(true);
            }
        });
    }
}
