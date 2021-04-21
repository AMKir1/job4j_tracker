package ru.job4j.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class StartUI {

    @Autowired
    private Store store;
    @Autowired
    private ConsoleInput input;

    public void add() {
        store.add(this.input.ask("What is your whole name: "));
    }

    public void print() throws InterruptedException {
       // for (String value : store.getAll()) {
       //     System.out.println(value);
       // }
        store.getAllByReact(System.out::println);
    }
}