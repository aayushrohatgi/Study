package dtos;

import java.util.*;

public class TimeMapBinary {
    Map<String, List<TimeValue>> kvStore;

    public TimeMapBinary() {
        kvStore = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        kvStore.computeIfAbsent(key, k -> new ArrayList<>()).add(new TimeValue(timestamp, value));
    }

    // This can be much neater, but I do not want to waste time here at the moment
    public String get(String key, int timestamp) {
        String retVal = "";
        if (kvStore.containsKey(key)) {
            var timeValueList = kvStore.get(key);
            if (null != timeValueList && !timeValueList.isEmpty()) {
                TimeValue valueListFirst = timeValueList.getFirst();
                if (valueListFirst.getTimestamp() < timestamp) {
                    TimeValue timeValue = valueListFirst;
                    int low = 0;
                    int high = timeValueList.size() - 1;
                    while (low <= high) {
                        int mid = (low + high) / 2;
                        var current = timeValueList.get(mid);
                        if (current.getTimestamp() == timestamp) {
                            timeValue = current;
                            break;
                        } else if (current.getTimestamp() < timestamp) {
                            timeValue = current;
                            low = mid + 1;
                        } else {
                           high = mid - 1;
                        }
                    }
                    retVal = timeValue.getValue();
                }
                if (valueListFirst.getTimestamp() == timestamp) {
                    retVal = valueListFirst.getValue();
                }
            }
        }
        return retVal;
    }
}

class TimeValue {
    int timestamp;
    String value;

    TimeValue(Integer timestamp, String value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getValue() {
        return value;
    }
}
