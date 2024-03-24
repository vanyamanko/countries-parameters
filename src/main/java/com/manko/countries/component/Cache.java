package com.manko.countries.component;

import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@SuppressWarnings("squid:S4684")
@Data
public class Cache {
    private Map<String, Object> hashMap = new ConcurrentHashMap<>();

    public void put(String key, Object value) {
        hashMap.put(key, value);
    }

    public Object get(String key) {
        return hashMap.get(key);
    }

    public void clear() {
        hashMap.clear();
    }
}