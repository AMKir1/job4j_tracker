package ru.job4j.start;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Timestamp;
import java.util.List;

public class HibernateRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Item item = create(new Item("Learn Hibernate"), sf);
            System.out.println(item);
            item.setName("Learn Hibernate 5.");
            update(item, sf);
            System.out.println(item);
            Item rsl = findById(item.getId(), sf);
            System.out.println(rsl);
            delete(rsl.getId(), sf);

            Item item2 = new Item();
            item2.setName("test2");
            item2.setCreated(new Timestamp(System.currentTimeMillis()));
            item2.setDescription("desc2");
            create(item2, sf);

            Item item3 = new Item();
            item3.setName("test3");
            item3.setDescription("desc3");
            create(item3, sf);

            Item item4 = new Item();
            item4.setCreated(new Timestamp(System.currentTimeMillis()));
            create(item4, sf);

            Item item5 = new Item();
            item5.setDescription("desc5");
            create(item5, sf);

            Item item6 = new Item();
            item3.setName("desc6");
            create(item6, sf);

            List<Item> list = findAll(sf);
            for (Item it : list) {
                System.out.println(it);
            }


        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static Item create(Item item, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public static void update(Item item, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(item);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(int id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = new Item(null);
        item.setId(id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
    }

    public static List<Item> findAll(SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.job4j.start.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public static Item findById(int id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
