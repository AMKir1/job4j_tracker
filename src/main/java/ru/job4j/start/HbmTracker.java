package ru.job4j.start;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;


public class HbmTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();


    @Override
    public void init() {

    }

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.save(item);
            t.commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        boolean result = false;
        Item i = findById(id);
        i.setName(item.getName());
        i.setDescription(item.getDescription());
        i.setCreated(item.getCreated());
        Session session = sf.openSession();
        Transaction t = null;
        try {
            t = session.beginTransaction();
            session.update(i);
            t.commit();
            result = true;
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        Session session = sf.openSession();
        Transaction t = null;
        boolean result = false;
        try {
            t = session.beginTransaction();
            Item item = new Item(null);
            item.setId(Integer.parseInt(id));
            session.delete(item);
            t.commit();
            result = true;
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        List<Item> result = null;
        Transaction t = null;
        try {
            t = session.beginTransaction();
            result = session.createQuery("from ru.job4j.start.Item").list();
            t.commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        List<Item> result = null;
        Transaction t = null;
        try {
            t = session.beginTransaction();
            result = session.createQuery("from ru.job4j.start.Item where name=:" + key).list();
            t.commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Item findById(String id) {
        Session session = sf.openSession();
        Transaction t = null;
        Item result = null;
        try {
            t = session.beginTransaction();
            result = session.get(Item.class, id);
            t.commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
