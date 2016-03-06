package com.phonebook.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.phonebook.model.Contact;
import com.phonebook.model.ContactHandler;

import org.hibernate.SessionFactory;
import org.hibernate.Session;

import org.hibernate.cfg.Configuration;

@Service
public class PhoneBookService {

	@Autowired
	private ContactHandler contactHandler;

	static SessionFactory sessionFactory;

	public PhoneBookService() {
		sessionFactory = new Configuration().configure()
				.buildSessionFactory();
	}

	public boolean isValid(String name, String phone) {
		return (name.length() > 2 && name.length() < 100 && phone.length() < 20 &&
			phone.replaceAll("\\D", "").length() > 1)? true : false;
	}

	public String fixName(String name) {
		return name.replaceAll("[^-\\w\\s()а-яА-Я]", "")
			.replaceAll("\\s+", " ").replaceAll("^\\s+", "").replaceAll("\\s+$", "");
	}

	public String fixPhone(String phone) {
		return phone.replaceAll("[^-+\\#\\d()]", "");
	}

	@Transactional
	public List<Contact> listContact() {
		return contactHandler.listContact(sessionFactory);
	}

	@Transactional
	public Contact getContact(Integer id) throws Exception {
		return contactHandler.getContact(sessionFactory, id);
	}

	@Transactional
	public void removeContact(Integer id) throws Exception {
		contactHandler.removeContact(sessionFactory, id);
	}

	@Transactional
	public void addContact(String name, String phone) throws Exception {
		contactHandler.addContact(sessionFactory, name, phone);
	}

	@Transactional
	public void editContact(Integer id, String name, String phone) throws Exception {
		contactHandler.editContact(sessionFactory, id, name, phone);
	}

	@Transactional
	public List<Contact> searchContact(String query) {
		return contactHandler.searchContact(sessionFactory, query);
	}
}