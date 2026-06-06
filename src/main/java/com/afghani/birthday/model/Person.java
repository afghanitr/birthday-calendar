package com.afghani.birthday.model;

public class Person {
	public Person(String name, String nik, String block) {
		super();
		this.name = name;
		this.nik = nik;
		this.block = block;
	}

	private String name;
    private String nik;
    private String block;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNik() {
		return nik;
	}
	public void setNik(String nik) {
		this.nik = nik;
	}
	
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", nik=" + nik + ", block=" + block + "]";
	}
}
