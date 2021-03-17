package ru.job4j.start;
/*
 * Class StartUI.
 * @author Kirillovykh Andrei (andykirill@gmail.com)
 * @version $Id$
 * @since 0.1
 */

import java.util.List;
import java.util.function.Consumer;

public class StartUI {
    /**
	 * Params.
	 */
	final private int[] range = new int[] {0, 1, 2, 3, 4, 5, 6};
	final private Input input;
	final private Store tracker;
	private int id = 0;
    private static Consumer<String> output;

	StartUI(Input input, Store tracker, Consumer<String> output) {
		this.input = input;
		this.tracker = tracker;
		this.output = output;
    }
	/**
	 * Main.
	 * @param args - first args.
	 */
	public static void main(String[] args) throws Exception {
		new StartUI(
				new ValidateInput(new ConsoleInput()),
				new SqlTracker(),
                System.out::println).init();
	}
	/**
	 * Init
	 */
    void init() throws Exception {
		MenuTracker menu = new MenuTracker(this.input, this.tracker, output);
		menu.fillAction();
		do {
			menu.show();
			menu.select(input.ask("Select: ", this.range));
		} while (!"y".equals(this.input.ask("Exit?(y)")));
	}
	/**
	 * Add new item.
	 */
	private void addNewItem() throws Exception {
        output.accept("Adding a new item");
		String itemId = Integer.toString(this.id++);
		String itemName = this.input.ask("Plesae, enter the item's name: ");
		String itemDesc = this.input.ask("Plesae, enter the item's description: ");
		Item item1 = new Item(itemName, itemDesc, System.currentTimeMillis(), itemId);
		this.tracker.add(item1);
        output.accept("Your item was added:");
        output.accept("Name: " + item1.getName() + "\n" + "Description: " + item1.getDescription() + "\n" + "Cretae: " + item1.getCreate() + "\n" + "Id: " + item1.getId() + "\n");
		this.init();
	}
	/**
	 * Show all items.
	 */
	private void showAllItems() throws Exception {
        output.accept("All Items");
		List<Item> items = this.tracker.findAll();
		int position = 0;
		if (items.size() > 0) {
			for (Item item : items) {
				if (item.getName() != null) {
					position++;
                    output.accept("Space: " + position + "\n" + "Name: " + item.getName() + "\n" + "Description: " + item.getDescription() + "\n" + "Cretae: " + item.getCreate() + "\n" + "Id: " + item.getId() + "\n");
				} else {
                    output.accept("NO Items");
				}
			}
		} else {
            output.accept("NO Items");
		}
		this.init();
	}
	/**
	 * Edit item.
	 */
	private void editItem() throws Exception {
        output.accept("Edit Item");
		String itemName = this.input.ask("Plesae, enter the item's name: ");
		String itemDesc = this.input.ask("Plesae, enter the item's description: ");
		String itemId =  input.ask("Plesae, enter the item's id, which you want to edit: ");
		Item editItem1 = new Item(itemName, itemDesc, System.currentTimeMillis(), itemId);
		this.tracker.replace("1", editItem1);
        output.accept("It's Edited item:" + "\n");
        output.accept("Name: " + editItem1.getName() + "\n" + "Description: " + editItem1.getDescription() + "\n" + "Id: " + editItem1.getId() + "\n");
		this.init();
	}
	/**
	 * Delete item.
	 */
	private void deleteItem() throws Exception {
        output.accept("Delete Item");
		String itemId = input.ask("Plesae, enter the item's id, which you want to delete: ");
		this.tracker.delete(itemId);
		this.init();
	}
	/**
	 * Find item by id.
	 */
	private void findItembyId() throws Exception {
		boolean id = false;
        output.accept("Find Items by Id");
		String itemId = input.ask("Plesae, enter the item's id, which you want to find: ");
		List<Item> items = this.tracker.findAll();
		for (Item item : items) {
            output.accept("It's your item:");
				showItem(item);
				id = true;
			}
		if (!id) {
            output.accept("NO Items");
		}
		this.init();
	}
	/**
	 * Find item by name.
	 */
	private void findItemsbyName() throws Exception {
		boolean name = false;
        output.accept("Find Items by Name");
		String itemKey = this.input.ask("Plesae, enter the item's key, which you want to find: ");
		List<Item> items = this.tracker.findAll();
		for (Item item : items) {
			if (item.getName().equals(itemKey)) {
                output.accept("It's your item:");
				showItem(item);
				name = true;
			}
		}
		if (!name) {
            output.accept("NO Items");
		}
		this.init();
	}

	private String showMenu() {
        output.accept("MENU");
        output.accept("0. Add new Item");
        output.accept("1. Show all items");
        output.accept("2. Edit item");
        output.accept("3. Delete item");
        output.accept("4. Find item by id");
        output.accept("5. Find item by name");
        output.accept("6. Exit Program");
		return input.ask("\nPlease, choose number for action: ");

	}
	private void showItem(Item item) {
        output.accept("Name: " + item.getName());
        output.accept("Description: " + item.getDescription());
        output.accept("Create: " + item.getCreate());
        output.accept("Id: " + item.getId());
	}
}