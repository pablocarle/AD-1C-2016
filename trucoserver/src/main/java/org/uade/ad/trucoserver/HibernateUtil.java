package org.uade.ad.trucoserver;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.uade.ad.trucoserver.entities.Baza;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.Mano;

public final class HibernateUtil {
	
    private static final SessionFactory sessionFactory;
    
    static {
        try {
        	 Configuration conf = new Configuration();
        	 conf.addAnnotatedClass(Mano.class);
        	 conf.addAnnotatedClass(Baza.class);
        	 conf.addAnnotatedClass(Jugador.class);
             sessionFactory = conf.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
