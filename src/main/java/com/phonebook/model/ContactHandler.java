package com.phonebook.model;

import com.phonebook.model.Contact;
import org.springframework.stereotype.Repository;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Query;

import org.slf4j.*;

import java.util.List;

@Repository
public class ContactHandler {

	final static Logger logger = LoggerFactory.getLogger(ContactHandler.class);

	public List<Contact> listContact(SessionFactory sessionFactory) {
		try {
			Session session = sessionFactory.openSession();
			List<Contact> lst = session.createQuery("from Contact").list();
			session.close();

			logger.info("Select all");

			return lst;
		
		} catch(Exception e) {
			return null;
		}
	}

	public void removeContact(SessionFactory sessionFactory, Integer id) 
		throws Exception {
		try {
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("delete Contact where id = :id");
			query.setParameter("id", new Long(id));

			session.beginTransaction();
			query.executeUpdate();
			session.getTransaction().commit();

			session.close();

			logger.info("Removed id={}", id);
		
		} catch(Exception e) {
			throw e;
		}
	}

	public Contact getContact(SessionFactory sessionFactory, Integer id) 
		throws Exception {
		try {
			Session session = sessionFactory.openSession();

			session.beginTransaction();
			List<Contact> lst = session.createQuery("from Contact where id = :id")
				.setParameter("id", new Long(id)).list();
			session.getTransaction().commit();

			session.close();

			return lst.get(0);
		
		} catch(Exception e) {
			throw e;
		}
	}

	public void addContact(SessionFactory sessionFactory, String name, String phone) 
		throws Exception {
		try {
			Session session = sessionFactory.openSession();
			Contact entity = new Contact(name, phone);

			session.beginTransaction();
			session.save(entity);
			session.getTransaction().commit();

			session.close();

			logger.info("Added new contact: {}, {}", name, phone);
		
		} catch(Exception e) {
			throw e;
		}
	}

	public void editContact(SessionFactory sessionFactory, Integer id, 
		String name, String phone)  throws Exception {
		try {
			Session session = sessionFactory.openSession();

			Query query = session.createQuery(
				"update Contact set name = :name, phone = :phone where id = :id");
			query.setParameter("id", new Long(id));
			query.setParameter("name", name);
			query.setParameter("phone", phone);

			session.beginTransaction();
			query.executeUpdate();
			session.getTransaction().commit();

			session.close();

			logger.info("Updated id={}", id);
		} catch(Exception e) {
			throw e;
		}
	}

	public List<Contact> searchContact(SessionFactory sessionFactory, 
		String query) {
		try {
			Session session = sessionFactory.openSession();
			List<Contact> lst = session
				.createQuery("from Contact where name like :query or phone like :query")
				.setParameter("query", "%" + query + "%").list();
			session.close();

			logger.info("Search query={}", query);

			return lst;

		} catch(Exception e) {
			return null;
		}
	}
}