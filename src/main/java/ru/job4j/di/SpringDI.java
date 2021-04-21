package ru.job4j.di;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDI {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.job4j.di");
        context.refresh();
        Store store = context.getBean(Store.class);
        store.add("Petr Arsentev");
        store.getAllByReact(System.out::println);
        Store another = context.getBean(Store.class);
        //another.getAll().forEach(System.out::println);
        another.getAllByReact(System.out::println);
    }
}
