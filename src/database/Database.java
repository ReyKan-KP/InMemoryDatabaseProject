package database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Database implements Serializable {
    @JsonProperty
    private final Map<String, Table> tables = new HashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String DATA_FOLDER = "data";
    private boolean transactionActive = false;
    private Map<String, Table> backupTables;

    public Database() {
        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public void createTable(String tableName) {
        tables.putIfAbsent(tableName, new Table());
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }

    public void insertRecord(String tableName, String id, DbRecord record) {
        if (transactionActive && backupTables == null) {
            backupTables = deepCopyTables();
        }
        Table table = tables.computeIfAbsent(tableName, k -> new Table());
        if (table.get(id) == null) {
            table.insert(id, record);
            System.out.println("Record inserted successfully.");
        } else {
            System.out.println(
                    "Record with ID '" + id + "' already exists in table '" + tableName + "'. No changes made.");
        }
    }

    public DbRecord getRecord(String tableName, String id) {
        Table table = tables.get(tableName);
        return (table != null) ? table.get(id) : null;
    }

    public void deleteRecord(String tableName, String id) {
        if (transactionActive && backupTables == null) {
            backupTables = deepCopyTables();
        }
        Table table = tables.get(tableName);
        if (table != null) {
            table.delete(id);
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("Table not found.");
        }
    }

    public void beginTransaction() {
        if (transactionActive) {
            System.out.println("Transaction already active.");
        } else {
            transactionActive = true;
            backupTables = deepCopyTables();
            System.out.println("Transaction started.");
        }
    }

    public void commit() {
        if (transactionActive) {
            transactionActive = false;
            backupTables = null; // Discard the backup as changes are now permanent
            System.out.println("Transaction committed.");
        } else {
            System.out.println("No active transaction to commit.");
        }
    }

    public void rollback() {
        if (transactionActive) {
            tables.clear();
            tables.putAll(backupTables); // Restore backup
            transactionActive = false;
            backupTables = null; // Clear backup after restoring
            System.out.println("Transaction rolled back.");
        } else {
            System.out.println("No active transaction to roll back.");
        }
    }

    private Map<String, Table> deepCopyTables() {
        Map<String, Table> copy = new HashMap<>();
        for (Map.Entry<String, Table> entry : tables.entrySet()) {
            copy.put(entry.getKey(), entry.getValue().deepCopy());
        }
        return copy;
    }

    public void createIndex(String tableName, String fieldName) {
        Table table = tables.get(tableName);
        if (table != null) {
            table.createIndex(fieldName);
            System.out.println("Index created for field: " + fieldName);
        } else {
            System.out.println("Table not found.");
        }
    }

    public void saveToFile(String filename) {
        try {
            File file = new File(DATA_FOLDER + File.separator + filename);
            objectMapper.writeValue(file, tables);
            System.out.println("Database saved to file: " + file.getPath());
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        try {
            File file = new File(DATA_FOLDER + File.separator + filename);
            Map<String, Table> loadedTables = objectMapper.readValue(file,
                    new TypeReference<Map<String, Table>>() {
                    });
            tables.clear();
            tables.putAll(loadedTables);
            System.out.println("Database loaded from file: " + file.getPath());
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }

    public void updateRecord(String tableName, String id, String fieldName, String fieldValue) {
        Table table = tables.get(tableName);
        if (table != null) {
            DbRecord record = table.get(id);
            if (record != null) {
                record.put(fieldName, fieldValue);
                System.out.println("Record updated.");
            } else {
                System.out.println("Record not found.");
            }
        } else {
            System.out.println("Table not found.");
        }
    }
}
