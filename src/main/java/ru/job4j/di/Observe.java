package ru.job4j.di;

public interface Observe<T> {
    void receive(T model);
}
