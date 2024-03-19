package com.manko.countries.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CacheTest {
    private Cache cache;

    @BeforeEach
    void setUp() {
        cache = new Cache();
    }

    @Test
    void testPutAndGet() {
        String key = "key";
        String value = "value";

        cache.put(key, value);
        Object retrievedValue = cache.get(key);

        assertEquals(value, retrievedValue);
    }

    @Test
    void testGetNonexistentKey() {
        String key = "nonexistentKey";

        Object retrievedValue = cache.get(key);

        assertNull(retrievedValue);
    }

    @Test
    void testClear() {
        String key = "key";
        String value = "value";

        cache.put(key, value);
        cache.clear();
        Object retrievedValue = cache.get(key);

        assertNull(retrievedValue);
    }
}