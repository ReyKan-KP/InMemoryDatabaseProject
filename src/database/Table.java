package database;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Table implements Serializable {
    @JsonProperty
    private final Map<String, DbRecord> records = new HashMap<>();
    @JsonProperty
    private final Map<String, Map<Object, String>> indexes = new HashMap<>();

    @JsonProperty
    public void insert(String id, DbRecord record) {
        records.put(id, record);
    }

    @JsonProperty
    public DbRecord get(String id) {
        return records.get(id);
    }

    @JsonProperty
    public void delete(String id) {
        records.remove(id);
    }

    public void createIndex(String fieldName) {
        indexes.put(fieldName, new HashMap<>());
        for (Map.Entry<String, DbRecord> entry : records.entrySet()) {
            DbRecord record = entry.getValue();
            Object value = record.get(fieldName);
            if (value != null) {
                indexes.get(fieldName).put(value, entry.getKey());
            }
        }
    }

    public DbRecord getByIndexedField(String fieldName, Object value) {
        String id = indexes.getOrDefault(fieldName, new HashMap<>()).get(value);
        return (id != null) ? records.get(id) : null;
    }

    public Table deepCopy() {
        Table newTable = new Table();
        for (Map.Entry<String, DbRecord> entry : records.entrySet()) {
            newTable.records.put(entry.getKey(), entry.getValue().deepCopy());
        }
        return newTable;
    }
}
