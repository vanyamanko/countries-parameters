package com.manko.countries.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class RequestCounterService {
    private final AtomicInteger counter = new AtomicInteger(0);

    public synchronized int increment() {
        return counter.incrementAndGet();
    }

    public synchronized int getCounter() {
        return counter.get();
    }
}
