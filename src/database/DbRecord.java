package database;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DbRecord implements Serializable {
    private final Map<String, Object> data;

    public DbRecord() {
        this.data = new HashMap<>();
    }

    public DbRecord(Map<String, Object> data) {
        this.data = new HashMap<>(data);
    }

    @JsonProperty
    public void put(String key, Object value) {
        data.put(key, value);
    }

    @JsonProperty
    public Object get(String key) {
        return data.get(key);
    }

    @JsonProperty
    public Map<String, Object> getData() {
        return new HashMap<>(data);
    }

    public DbRecord deepCopy() {
        return new DbRecord(new HashMap<>(data));
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
