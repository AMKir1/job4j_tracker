package ru.job4j.start;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	private String name;


	protected Item() {}

	protected Item(String name, int id) {
		this.name = name;
		this.id = id;
	}

	protected Item(String name) {
		this.name = name;
	}

	protected Item(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		 return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Item{"
				+ "name='" + name + '\''
				+ ", id='" + id + '\''
				+ '}';
	}
}