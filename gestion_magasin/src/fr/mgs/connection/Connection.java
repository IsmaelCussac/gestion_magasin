package fr.mgs.connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Class used only to manage the entity manager in DAO
 * 
 * @author Ismaël
 *
 */
public class Connection {
	
	protected EntityManagerFactory emf;

	public Connection() {}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public void initEmf() {
		setEmf(Persistence.createEntityManagerFactory("gestion_magasin"));
	}
	
	public void initEmf(String base) {
		setEmf(Persistence.createEntityManagerFactory(base));
	}

	public EntityManager createEm() {
		return emf.createEntityManager();
	}

	public void close() {
		emf.close();
	}
}
