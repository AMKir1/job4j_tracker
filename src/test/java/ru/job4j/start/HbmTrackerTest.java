package ru.job4j.start;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;


public class HbmTrackerTest {

    private final HbmTracker ht = new HbmTracker();

    @Before
    public void setUp() {
        ht.add(new Item("test 1"));
        ht.add(new Item("test 2"));
        ht.add(new Item("test 3"));
        ht.add(new Item("test 4"));
    }

    @Test
    public void whenAdd() {
        ht.add(new Item("test 5"));
        List<Item> items = ht.findAll();
        assertThat(items.size(), is(5));
        assertThat(items.get(4).getName(), is("test 5"));
    }

    @Test
    public void whenReplace() {
        List<Item> items = ht.findAll();
        assertThat(items.size(), is(4));
        assertThat(items.get(2).getName(), is("test 3"));
        Item replace = items.get(2);
        replace.setName("REPLACE");
        ht.replace("2", replace);
        assertThat(items.get(2).getName(), is("REPLACE"));
    }

    @Test
    public void whenDelete() {
        assertThat(ht.findAll().size(), is(4));
        ht.delete("2");
        assertThat(ht.findAll().size(), is(3));
    }

    @Test
    public void whenFindAll() {
        List<Item> items = ht.findAll();
        assertThat(items.size(), is(4));
        assertThat(items.get(3).getName(), is("test 4"));
    }

    @Test
    public void whenFindByName() {
        ht.add(new Item("test 4"));
        List<Item> items = ht.findByName("test 4");
        assertThat(items.size(), is(2));
    }

    @Test
    public void whenFindById() {
        assertThat(ht.findById("3").getName(), is("test 3"));
    }
}
