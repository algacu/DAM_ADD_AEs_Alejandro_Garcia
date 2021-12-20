package es.add.ae5;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Principal {

		public static void main(String[] args) {
			
			try {
						
				// Carga la configuracion y crea un session factory
				Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
				configuration.addClass(Libro.class);
				ServiceRegistry registry = new
				StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
				SessionFactory sessionFactory = configuration.buildSessionFactory(registry);

				// Abre una nueva session de la session factory
				Session session = sessionFactory.openSession();
				session.beginTransaction();			
				
				Vista vista = new Vista();
				Biblioteca biblioteca = new Biblioteca();
				Controlador controlador = new Controlador(vista, biblioteca, session);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
