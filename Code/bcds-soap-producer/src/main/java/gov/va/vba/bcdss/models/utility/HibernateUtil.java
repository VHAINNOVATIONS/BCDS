package gov.va.vba.bcdss.models.utility;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("deprecation")
public class HibernateUtil {
	
	public static final SessionFactory sessionFactory;
	
	static {
		try {
			Configuration config = new Configuration().configure();
			sessionFactory = config.buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFacotyr creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static final ThreadLocal session = new ThreadLocal();
	
	@SuppressWarnings("unchecked")
	public static Session currentSession() throws HibernateException {
		Session s = (Session) session.get();
		
		if (s == null) {
			s = sessionFactory.openSession();
			session.set(s);
		}
		return s;
	}
	
	@SuppressWarnings("unchecked")
	public static void closeSession() throws HibernateException {
		Session s = (Session) session.get();
		
		if (s != null)
			s.close();
		session.set(null);
	}
}