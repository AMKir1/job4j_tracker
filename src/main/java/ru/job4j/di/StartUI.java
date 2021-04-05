package ru.job4j.di;

public class StartUI {

    private Store store;
    private ConsoleInput input;

    public StartUI(Store store, ConsoleInput input) {
        this.store = store;
        this.input = input;
    }

    public void add() {
        store.add(this.input.ask("What is your whole name: "));
    }

    public void print() {
        for (String value : store.getAll()) {
            System.out.println(value);
        }
    }
}