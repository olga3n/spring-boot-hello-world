package com.phonebook.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "phonebook", 
	uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Contact implements Serializable {

	public Contact() {};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotNull
	private String name;

	@Column
	@NotNull
	private String phone;

	public Contact(String name, String phone) {
		this.name = name;
		this.phone = phone;
	};

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setItem(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}
}