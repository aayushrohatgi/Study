package dtos;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TimeMap {
    Map<String, TreeMap<Integer, String>> kvStore;

    public TimeMap() {
        kvStore = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        kvStore.computeIfAbsent(key, k -> new TreeMap<>()).put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        String retVal = "";
        if (kvStore.containsKey(key)) {
            TreeMap<Integer, String> values = kvStore.get(key);
            var entry = values.floorEntry(timestamp);
            if (null != entry) {
                retVal = entry.getValue();
            }
        }
        return retVal;
    }
}
