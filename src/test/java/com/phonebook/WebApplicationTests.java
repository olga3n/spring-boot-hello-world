package com.phonebook;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.phonebook.model.Contact;

import java.security.SecureRandom;

import java.util.List;

public class WebApplicationTests extends TestCase {

	static final String Alph = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЭЮЯ";
	static final String AlphLower = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
	static final String AlphNumb = "0123456789";

	static SecureRandom rnd = new SecureRandom();

	public String randomName() {
		int l1 = rnd.nextInt(10) + 3;
		int l2 = rnd.nextInt(10) + 2;
		int l3 = rnd.nextInt(10) + 5;

		StringBuilder sb = new StringBuilder(l1 + l2 + l3 + 5);

		sb.append( Alph.charAt( rnd.nextInt(Alph.length()) ) );

		for(int i = 0; i < l1; i++)
			sb.append( AlphLower.charAt( rnd.nextInt(AlphLower.length()) ) );

		sb.append(" ");

		sb.append( Alph.charAt( rnd.nextInt(Alph.length()) ) );

		for(int i = 0; i < l2; i++)
			sb.append( AlphLower.charAt( rnd.nextInt(AlphLower.length()) ) );

		sb.append(" ");

		sb.append( Alph.charAt( rnd.nextInt(Alph.length()) ) );

		for(int i = 0; i < l3; i++)
			sb.append( AlphLower.charAt( rnd.nextInt(AlphLower.length()) ) );

		return sb.toString();
	}

	public String randomPhone() {

		StringBuilder sb = new StringBuilder(18);

		sb.append("+7-(");

		for(int i = 0; i < 3; i++)
			sb.append( AlphNumb.charAt( rnd.nextInt(AlphNumb.length()) ) );

		sb.append(")-");

		for(int i = 0; i < 2; i++)
			sb.append( AlphNumb.charAt( rnd.nextInt(AlphNumb.length()) ) );

		sb.append("-");

		for(int i = 0; i < 2; i++)
			sb.append( AlphNumb.charAt( rnd.nextInt(AlphNumb.length()) ) );

		sb.append("-");

		for(int i = 0; i < 3; i++)
			sb.append( AlphNumb.charAt( rnd.nextInt(AlphNumb.length()) ) );

		return sb.toString();
	}

	public void testApp() {
		SessionFactory sessionFactory = new Configuration()
			.configure("test.hibernate.cfg.xml")
			.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		int N = 12;

		for(int i = 0; i < N; i++)
		{
			String name = randomName();

			List result = session
				.createQuery("from Contact where name=:name")
				.setParameter("name", name)
				.list();
			
			if (result.isEmpty())
			{
				Contact entity = new Contact(name, randomPhone());
				session.save(entity);
			}
		}
		
		session.getTransaction().commit();

		session.close();
	}
}
