package org.example;

import java.util.Map;

public class HashMapStatusStorage implements StatusStorage {

    private final Map<Integer, Status> map;

    public HashMapStatusStorage(Map<Integer, Status> map) {
        this.map = map;
    }

    @Override
    public void saveStatus(int id, Status status) {
        map.put(id, status);
    }

    @Override
    public Status getStatus(int id) {
        return map.get(id);
    }

    @Override
    public void cleanStorage() {
        map.clear();
    }
}
