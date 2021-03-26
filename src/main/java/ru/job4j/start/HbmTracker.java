package ru.job4j.start;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class HbmTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    public static void main(String[] args) throws Exception {
        HbmTracker ht = new HbmTracker();
        Item item = new Item(2, "Test 222");
        ht.add(item);
        ht.replace("2", item);
//        ht.delete("33");
        System.out.println(ht.findById("37").toString());
        ht.findByName("Test 1").forEach(System.out::println);
        ht.findAll().forEach(System.out::println);
    }

    @Override
    public void init() {
    }

    @Override
    public Item add(Item item) {
        upsert(s -> s.save(item));
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        Item olditem = findById(id);
        olditem.setName(item.getName());
        olditem.setDescription(item.getDescription());
        olditem.setCreated(item.getCreated());
        return upsert(s -> s.update(olditem));
    }

    @Override
    public boolean delete(String id) {
        return upsert(s -> s.delete(new Item(Integer.parseInt(id), null)));
    }

    @Override
    public List<Item> findAll() {
        return get(s -> s.createQuery("from ru.job4j.start.Item").list());
    }

    @Override
    public List<Item> findByName(String key) {
        return get(s -> s.createQuery("from ru.job4j.start.Item where name = \'" + key + "\'").list());
    }

    @Override
    public Item findById(String id) {
        return get(s -> s.get(Item.class, Integer.parseInt(id)));
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    private <T> T get(Function<Session, T> command) {
        final Session s = sf.openSession();
        final Transaction tn = s.beginTransaction();
        try {
            T res = command.apply(s);
            tn.commit();
            return res;
        } catch (final Exception e) {
            tn.rollback();
            e.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }

    private boolean upsert(Consumer<Session> command) {
        final Session s = sf.openSession();
        final Transaction tn = s.beginTransaction();
        try {
            command.accept(s);
            tn.commit();
            return true;
        } catch (final Exception e) {
            tn.rollback();
            e.printStackTrace();
        } finally {
            s.close();
        }
        return false;
    }


}
