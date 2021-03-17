package ru.job4j.start;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class SqlTracker implements Store {
    private Connection cn;

    SqlTracker() {
        init();
    }

    SqlTracker(Connection cn) {
        this.cn = cn;
    }

    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
           cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    @Override
    public Item add(Item item) {
        Item result = null;
        try (PreparedStatement st = cn.prepareStatement("INSERT INTO items(name) VALUES ('" + item.getName() + "')", Statement.RETURN_GENERATED_KEYS)) {
            st.executeUpdate();
            st.getGeneratedKeys();
            result = item;
        } catch (SQLException e) {
            System.out.println("DB error: " + e);
        }
        return result;
    }

    @Override
    public boolean replace(String id, Item item) {
        boolean result = false;
        try (PreparedStatement st = cn.prepareStatement("UPDATE items SET name = '" + item.getName() + "' WHERE id = '" + id + "'")) {
            result = st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("DB error: " + e);
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        try (PreparedStatement st = cn.prepareStatement("DELETE FROM items WHERE id = '" + id + "'")) {
            result = st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("DB error: " + e);
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        List<Item> listItems = new ArrayList<>();
        try (Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("select * from items")) {
            while (rs.next()) {
                listItems.add(new Item(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.err.println("DB error: " + e);
        }
        return listItems;
    }

    @Override
    public List<Item> findByName(String name) {
        List<Item> listItems = new ArrayList<>();
        try (PreparedStatement st = cn.prepareStatement("select * from items WHERE name = '" + name + "'")) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                listItems.add(new Item(rs.getInt("id"), rs.getString("name")));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("DB error: " + e);
        }
        return listItems;
    }

    @Override
    public Item findById(String id) {
        Item item = null;
        try (PreparedStatement st = cn.prepareStatement("select * from items WHERE id = '" + id + "'")) {
            ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    item = new Item(rs.getInt("id"), rs.getString("name"));
                }
            rs.close();
        } catch (SQLException e) {
            System.err.println("DB error: " + e);
        }
        return item;
    }
}
