package org.uade.ad.trucoserver;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.uade.ad.trucoserver.entities.Baza;
import org.uade.ad.trucoserver.entities.Carta;
import org.uade.ad.trucoserver.entities.CartaJugada;
import org.uade.ad.trucoserver.entities.Categoria;
import org.uade.ad.trucoserver.entities.Chico;
import org.uade.ad.trucoserver.entities.EnvidoEnvite;
import org.uade.ad.trucoserver.entities.Envite;
import org.uade.ad.trucoserver.entities.EnvitesManoPareja;
import org.uade.ad.trucoserver.entities.Grupo;
import org.uade.ad.trucoserver.entities.GrupoDetalle;
import org.uade.ad.trucoserver.entities.Jugador;
import org.uade.ad.trucoserver.entities.LogJuego;
import org.uade.ad.trucoserver.entities.Mano;
import org.uade.ad.trucoserver.entities.Pareja;
import org.uade.ad.trucoserver.entities.Partida;
import org.uade.ad.trucoserver.entities.PartidaCerrada;
import org.uade.ad.trucoserver.entities.TipoPartida;
import org.uade.ad.trucoserver.entities.TrucoEnvite;

public final class HibernateUtil {
	
    private static final SessionFactory sessionFactory;
    
    static {
        try {
        	 Configuration conf = new Configuration();
        	 conf.addAnnotatedClass(Mano.class);
        	 conf.addAnnotatedClass(Baza.class);
        	 conf.addAnnotatedClass(Jugador.class);
        	 conf.addAnnotatedClass(Categoria.class);
        	 conf.addAnnotatedClass(Grupo.class);
        	 conf.addAnnotatedClass(LogJuego.class);
        	 conf.addAnnotatedClass(Carta.class);
        	 conf.addAnnotatedClass(Envite.class);
        	 conf.addAnnotatedClass(CartaJugada.class);
        	 conf.addAnnotatedClass(Pareja.class);
        	 conf.addAnnotatedClass(Chico.class);
        	 conf.addAnnotatedClass(TipoPartida.class);
        	 conf.addAnnotatedClass(Partida.class);
        	 conf.addAnnotatedClass(PartidaCerrada.class);
        	 conf.addAnnotatedClass(GrupoDetalle.class);
        	 conf.addAnnotatedClass(TrucoEnvite.class);
        	 conf.addAnnotatedClass(EnvidoEnvite.class);
        	 conf.addAnnotatedClass(EnvitesManoPareja.class);
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
