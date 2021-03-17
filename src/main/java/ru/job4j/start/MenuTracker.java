package ru.job4j.start;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Kirillovykh Andrei (andykirill@gmail.com)
 * @version $Id$
 * @since 0.1
 */

class Exit extends BaseAction {
    public Exit(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Store tracker) {
    }
}


public class MenuTracker {

    private final Input input;
    private final Store tracker;
    private final UserAction[] actions = new UserAction[7];
    private int id;
    private int position = 0;
    private final Consumer<String> output;

    public MenuTracker(Input input, Store tracker, Consumer<String> output) {
        this.input = input;
        this.tracker = tracker;
        this.output = output;
    }

    private void showItem(Item item) {
        output.accept("______________________");
        output.accept("Name: " + item.getName());
        output.accept("Description: " + item.getDescription());
        output.accept("Create: " + item.getCreate());
        output.accept("Id: " + item.getId());
        output.accept("______________________");
    }

    public void fillAction() {
        this.actions[position++] = this.new AddItem(0, "Add the new Item");
        this.actions[position++] = this.new ShowAllItems(1, "Show all Items");
        this.actions[position++] = new EditItem(2, "Edit Item");
        this.actions[position++] = this.new DeleteItem(3, "Delete Item");
        this.actions[position++] = this.new FindItemById(4, "Find Items by Id");
        this.actions[position++] = this.new FindItemsByName(5, "Find Items by Name");
        this.actions[position++] = new Exit(6, "Exit Program");
    }

    public void addAction(UserAction action) {
        this.actions[position++] = action;
    }

    public void select(int key) throws Exception {
        this.actions[key].execute(this.input, this.tracker);
    }

    public void show() {
        for (UserAction action : this.actions) {
            if (action != null) {
                output.accept(action.info());
            }
        }
    }

    private class AddItem extends BaseAction {
        private AddItem(int key, String name) {
            super(key, name);
        }

        public void execute(Input input, Store tracker) throws Exception {
            String itemId = Integer.toString(id++);
            String itemName = input.ask("Plesae, enter the item's name: ");
            String itemDesc = input.ask("Plesae, enter the item's description: ");
            Item item = new Item(itemName, itemDesc, System.currentTimeMillis(), itemId);
            if (tracker.add(item) != null) {
                output.accept("Your item was added:");
                showItem(item);
            } else {
                output.accept("Your item wasn't added.");
            }
        }
    }

    private class ShowAllItems extends BaseAction {
        private ShowAllItems(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Store tracker) throws Exception {
            List<Item> items = tracker.findAll();
            if (items.size() > 0) {
                for (Item item : items) {
                    if (item != null) {
                        showItem(item);
                    }
                }
            } else {
                output.accept("No Items");
            }
        }
    }

    private class DeleteItem extends BaseAction {
        private DeleteItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Store tracker) throws Exception {
            boolean find = false;
            String itemId = input.ask("Plesae, enter the item's id, which you want to delete: ");
            if (tracker.findById(itemId) != null) {
                    tracker.delete(itemId);
                    output.accept("Your item was deleted");
                    find = true;
            }
            if (!find) {
                output.accept("Your item wasn't deleted");
            }
        }
    }

    private class FindItemById extends BaseAction {
        private FindItemById(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Store tracker) throws Exception {
            boolean find = false;
            String itemId = input.ask("Plesae, enter the item's id, which you want to find: ");
            List<Item> items = tracker.findAll();
            for (Item item : items) {
                if (item.getId().equals(itemId)) {
                    output.accept("It's your item:");
                    showItem(item);
                    find = true;
                }
            }
            if (!find) {
                output.accept("We can't find your Item. Sorry:(");
            }
        }
    }

    private class FindItemsByName extends BaseAction {
        private FindItemsByName(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Store tracker) throws Exception {
            boolean find = false;
            String itemName = input.ask("Plesae, enter the item's key, which you want to find: ");
            List<Item> items = tracker.findAll();
            for (Item item : items) {
                if (item.getName().equals(itemName)) {
                    output.accept("It's your item:");
                    showItem(item);
                    find = true;
                }
            }
            if (!find) {
                output.accept("No Items");
            }
        }
    }

    private class EditItem extends BaseAction {
        private EditItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Store tracker) throws Exception {
            String itemId = input.ask("Plesae, enter the item's id, which you want to edit: ");
            if (tracker.findById(itemId) != null) {
                String itemName = input.ask("Plesae, enter the item's name: ");
                String itemDesc = input.ask("Plesae, enter the item's description: ");
                Item editItem = new Item(itemName, itemDesc, System.currentTimeMillis(), itemId);
                tracker.replace(itemId, editItem);
            } else {
                output.accept("We can't find your Item. Sorry:(");
                output.accept("But, You can try to edit item again :)");
            }
        }
    }
}