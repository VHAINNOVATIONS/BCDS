package main.java.gov.va.vba.persistence.models.events;

import org.hibernate.SessionFactory;

import main.java.gov.va.vba.persistence.models.utility.HibernateUtil;

public class Main {
	
	public static void main() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	}
	
	
}