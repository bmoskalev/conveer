package org.example;

public interface StatusStorage {
    void saveStatus(int id, Status status);

    Status getStatus(int id);

    void cleanStorage();
}
