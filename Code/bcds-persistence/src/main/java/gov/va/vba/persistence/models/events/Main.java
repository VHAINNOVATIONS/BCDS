package main.java.gov.va.vba.persistence.models.events;

import java.util.List;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;

import main.java.gov.va.vba.persistence.models.utility.HibernateUtil;

public class Main {
	
	public static void main() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	}
	
	
}