package ru.job4j.start;

public class Item {
	/**
	* Params.
	*/
	private String name;
	/**
	* Params.
	*/
	private String description;
	/**
	* Params.
	*/
	private long create;
	/**
	* Params.
	*/
	private int idint = 0;
	public String id = String.valueOf(idint);

	
	/**
	* Default Constructor.
	*/
	protected Item() {
	}
	/**
	* Constructor
	* @param name - first ards.
	* @param description - second ards.
	* @param create - third ards.
	*/
	protected Item(String name, String description, long create, String id) {
		this.name = name;
		this.description = description;
		this.create = create;
		this.id = id;
		this.idint++;
	}

	protected Item(String name) {
		this.name = name;
	}

	protected Item(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	* Get Name.
	* @return this.name.
	*/
	public String getName() {
		return this.name;
	}
	/**
	* Get Description.
	* @return this.description.
	*/
	public String getDescription() {
		return this.description;
	}
	/**
	* Get Create.
	* @return this.create.
	*/
	public long getCreate() {
		return this.create;
	}
	/**
	* Get Id.
	* @return this.id.
	*/
	public String getId() {
		 return this.id;
	}
	/**
	* Set Id.
	* @param id - first ards.
	*/
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Item{"
				+ "name='" + name + '\''
				+ ", description='" + description + '\''
				+ ", create=" + create
				+ ", idint=" + idint
				+ ", id='" + id + '\''
				+ '}';
	}
}