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

    /**
     * Return the Entity manager Factory
     * 
     * @return emf entity manager factory
     */
    public EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * Set the Entity Manager Factory
     * 
     * @param emf
     *            entity manager factory
     */
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Initialize the data source for the Entity Manager Factory
     * 
     * @param ds
     *            the data source
     */
    public void initEmf(DataSource ds) {
        setEmf(Persistence.createEntityManagerFactory(ds.toString()));
    }

    /**
     * Create the Entity Manager Factory
     * 
     * @return emfCreation the Entity's creation
     */
    public EntityManager createEm() {
        return emf.createEntityManager();
    }

    /**
     * Close the Entity Manager Factory
     */
    public void close() {
        emf.close();
    }
}
