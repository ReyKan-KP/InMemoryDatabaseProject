package gui;

import database.*;
import javax.swing.*;
import java.awt.*;
// import java.awt.event.ActionEvent;

public class DatabaseGUI extends JFrame {
    private Database db;
    private JTextField tableNameField, recordIdField, numFieldsField, fieldNameField, fieldValueField, filenameField;
    private JTextArea outputArea;
    private JButton insertButton, updateButton, getButton, deleteButton, saveButton, loadButton, createIndexButton;
    private JButton beginTransactionButton, commitButton, rollbackButton;

    public DatabaseGUI(Database db) {
        this.db = db;
        setTitle("Database Management System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Insert Record", createInsertPanel());
        tabbedPane.addTab("Get Record", createGetPanel());
        tabbedPane.addTab("Update Record", createUpdatePanel());
        tabbedPane.addTab("Delete Record", createDeletePanel());
        tabbedPane.addTab("Save/Load", createSaveLoadPanel());
        tabbedPane.addTab("Create Index", createIndexPanel());
        tabbedPane.addTab("Transaction", createTransactionPanel());

        // Set color for each tab
        tabbedPane.setBackgroundAt(0, Color.CYAN);
        tabbedPane.setBackgroundAt(1, Color.ORANGE);
        tabbedPane.setBackgroundAt(2, Color.MAGENTA);
        tabbedPane.setBackgroundAt(3, Color.PINK);
        tabbedPane.setBackgroundAt(4, Color.YELLOW);
        tabbedPane.setBackgroundAt(5, Color.GREEN);
        tabbedPane.setBackgroundAt(6, Color.LIGHT_GRAY);

        add(tabbedPane, BorderLayout.CENTER);

        outputArea = new JTextArea(8, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private JPanel createInsertPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel tableLabel = new JLabel("Table Name:");
        JLabel recordLabel = new JLabel("Record ID:");
        JLabel numFieldsLabel = new JLabel("Number of Fields:");

        tableNameField = new JTextField(15);
        recordIdField = new JTextField(15);
        numFieldsField = new JTextField(5);

        insertButton = new JButton("Insert Record");
        insertButton.addActionListener(e -> handleInsert());

        // Layout and center alignment
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(tableLabel, gbc);
        gbc.gridx = 1;
        panel.add(tableNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(recordLabel, gbc);
        gbc.gridx = 1;
        panel.add(recordIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(numFieldsLabel, gbc);
        gbc.gridx = 1;
        panel.add(numFieldsField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(insertButton, gbc);

        return panel;
    }

    private void handleInsert() {
        String tableName = tableNameField.getText();
        String recordId = recordIdField.getText();
        int numFields;
        try {
            numFields = Integer.parseInt(numFieldsField.getText().trim());
        } catch (NumberFormatException e) {
            outputArea.append("Please enter a valid number of fields.\n");
            return;
        }

        DbRecord record = new DbRecord();
        for (int i = 0; i < numFields; i++) {
            String fieldName = JOptionPane.showInputDialog(this, "Enter field name:");
            String fieldValue = JOptionPane.showInputDialog(this, "Enter field value:");
            if (fieldName != null && fieldValue != null) {
                record.put(fieldName, fieldValue);
            } else {
                outputArea.append("Field entry cancelled.\n");
                return;
            }
        }
        db.insertRecord(tableName, recordId, record);
        outputArea.append("Record inserted successfully.\n");

        // Clear input fields
        tableNameField.setText("");
        recordIdField.setText("");
        numFieldsField.setText("");
    }

    private JPanel createGetPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel tableLabel = new JLabel("Table Name:");
        JLabel recordLabel = new JLabel("Record ID:");

        tableNameField = new JTextField(10);
        recordIdField = new JTextField(10);

        getButton = new JButton("Get Record");
        getButton.addActionListener(e -> {
            String tableName = tableNameField.getText();
            String recordId = recordIdField.getText();
            DbRecord record = db.getRecord(tableName, recordId);
            outputArea.append((record != null) ? record.toString() + "\n" : "Record not found.\n");

            // Clear input fields
            tableNameField.setText("");
            recordIdField.setText("");
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(tableLabel, gbc);
        gbc.gridx = 1;
        panel.add(tableNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(recordLabel, gbc);
        gbc.gridx = 1;
        panel.add(recordIdField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(getButton, gbc);

        return panel;
    }

    private JPanel createUpdatePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel tableLabel = new JLabel("Table Name:");
        JLabel recordLabel = new JLabel("Record ID:");
        JLabel fieldLabel = new JLabel("Field Name:");
        JLabel valueLabel = new JLabel("New Value:");

        tableNameField = new JTextField(10);
        recordIdField = new JTextField(10);
        fieldNameField = new JTextField(10);
        fieldValueField = new JTextField(10);

        updateButton = new JButton("Update Record");
        updateButton.addActionListener(e -> handleUpdate());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(tableLabel, gbc);
        gbc.gridx = 1;
        panel.add(tableNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(recordLabel, gbc);
        gbc.gridx = 1;
        panel.add(recordIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(fieldLabel, gbc);
        gbc.gridx = 1;
        panel.add(fieldNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(valueLabel, gbc);
        gbc.gridx = 1;
        panel.add(fieldValueField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(updateButton, gbc);

        return panel;
    }

    private void handleUpdate() {
        String tableName = tableNameField.getText();
        String recordId = recordIdField.getText();
        String fieldName = fieldNameField.getText();
        String fieldValue = fieldValueField.getText();

        DbRecord record = db.getRecord(tableName, recordId);
        if (record != null) {
            record.put(fieldName, fieldValue);
            outputArea.append("Record updated.\n");

            // Clear input fields
            tableNameField.setText("");
            recordIdField.setText("");
            fieldNameField.setText("");
            fieldValueField.setText("");
        } else {
            outputArea.append("Record not found.\n");
        }
    }

    private JPanel createDeletePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel tableLabel = new JLabel("Table Name:");
        JLabel recordLabel = new JLabel("Record ID:");

        tableNameField = new JTextField(10);
        recordIdField = new JTextField(10);

        deleteButton = new JButton("Delete Record");
        deleteButton.addActionListener(e -> {
            String tableName = tableNameField.getText();
            String recordId = recordIdField.getText();
            db.deleteRecord(tableName, recordId);
            outputArea.append("Record deleted.\n");

            // Clear input fields
            tableNameField.setText("");
            recordIdField.setText("");
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(tableLabel, gbc);
        gbc.gridx = 1;
        panel.add(tableNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(recordLabel, gbc);
        gbc.gridx = 1;
        panel.add(recordIdField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(deleteButton, gbc);

        return panel;
    }

    // Create Save/Load Panel
    private JPanel createSaveLoadPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel filenameLabel = new JLabel("Filename:");
        filenameField = new JTextField(15);

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String filename = filenameField.getText();
            db.saveToFile(filename);
            outputArea.append("Database saved to " + filename + ".\n");

            // Clear input field
            filenameField.setText("");
        });

        loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
            String filename = filenameField.getText();
            db.loadFromFile(filename);
            outputArea.append("Database loaded from " + filename + ".\n");

            // Clear input field
            filenameField.setText("");
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(filenameLabel, gbc);
        gbc.gridx = 1;
        panel.add(filenameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(saveButton, gbc);
        gbc.gridx = 1;
        panel.add(loadButton, gbc);

        return panel;
    }

    // Create Index Panel
    private JPanel createIndexPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel tableLabel = new JLabel("Table Name:");
        JLabel fieldLabel = new JLabel("Field for Index:");
        tableNameField = new JTextField(10);
        fieldNameField = new JTextField(10);

        createIndexButton = new JButton("Create Index");
        createIndexButton.addActionListener(e -> {
            String tableName = tableNameField.getText();
            String fieldName = fieldNameField.getText();
            db.createIndex(tableName, fieldName);
            outputArea.append("Index created on " + fieldName + " in table " + tableName + ".\n");

            // Clear input fields
            tableNameField.setText("");
            fieldNameField.setText("");
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(tableLabel, gbc);
        gbc.gridx = 1;
        panel.add(tableNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(fieldLabel, gbc);
        gbc.gridx = 1;
        panel.add(fieldNameField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(createIndexButton, gbc);

        return panel;
    }

    // Create Transaction Panel
    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        beginTransactionButton = new JButton("Begin Transaction");
        beginTransactionButton.addActionListener(e -> {
            db.beginTransaction();
            outputArea.append("Transaction started.\n");
        });

        commitButton = new JButton("Commit");
        commitButton.addActionListener(e -> {
            db.commit();
            outputArea.append("Transaction committed.\n");
        });

        rollbackButton = new JButton("Rollback");
        rollbackButton.addActionListener(e -> {
            db.rollback();
            outputArea.append("Transaction rolled back.\n");
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(beginTransactionButton, gbc);

        gbc.gridx = 1;
        panel.add(commitButton, gbc);

        gbc.gridx = 2;
        panel.add(rollbackButton, gbc);

        return panel;
    }

    public static void main(String[] args) {
        Database db = new Database();
        DatabaseGUI gui = new DatabaseGUI(db);
        gui.setVisible(true);
    }
}
