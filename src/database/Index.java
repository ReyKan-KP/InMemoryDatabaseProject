package database;

import java.util.HashMap;
import java.util.Map;

public class Index {
    private final Map<Object, String> indexMap = new HashMap<>();

    public void addToIndex(Object key, String id) {
        indexMap.put(key, id);
    }

    public String getByKey(Object key) {
        return indexMap.get(key);
    }

    public void removeFromIndex(Object key) {
        indexMap.remove(key);
    }
}
