package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.mgs.connection.Connection;
import fr.mgs.model.event.Action;
import fr.mgs.model.event.Event;
import fr.mgs.model.order.Order;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.User;

/**
 * Dao used to manage log monitor entity
 * 
 * @author Ismaël
 *
 */
public class EventDAO extends GenericDAO<Event, Integer> {

	public EventDAO(Connection connection) {
		super.connection = connection;
	}

	public EventDAO() {
	}

	/**
	 * store a event in database
	 * 
	 * @param the
	 *            event to add
	 */
	public void add(Event event) throws SQLException {
		beginTransaction();
		em.persist(event);
		commit();
		closeEm();
	}

	/**
	 * find an event using his id
	 * 
	 * @param event's id
	 */
	public Event find(String eventId) throws SQLException {
		loadEm();
		Event event = em.find(Event.class, eventId);
		closeEm();
		return event;
	}

	/**
	 * return all the stored users ordered by their first name at first, then
	 * their name
	 */
	public Collection<Event> findAll() throws SQLException {
		loadEm();
		TypedQuery<Event> query = em.createQuery("FROM events e order by e.date desc", Event.class);
		List<Event> result = query.getResultList();
		closeEm();
		return result;
	}
	
	/**
	 * return the given action's events
	 * 
	 * @param the action
	 */
	public List<Event> findEventsByAction(Action a) {
		loadEm();
		Query query = em.createQuery("SELECT e FROM events e WHERE e.action = :a");
		query.setParameter("a", a);
		return (List<Event>) query.getResultList();
	}
	
	/**
	 * return the given product's events
	 * 
	 * @param the action
	 */
	public List<Event> findEventsByProduct(int id) {
		loadEm();
		Query query = em.createQuery("SELECT e FROM events e WHERE e.product = :p");
		query.setParameter("p", id);
		return (List<Event>) query.getResultList();
	}
	
	/**
	 * return the given store keeper's events
	 * 
	 * @param the action
	 */
	public List<Event> findEventsByStoreKeeper(String id) {
		loadEm();
		Query query = em.createQuery("SELECT e FROM events e WHERE e.storeKeeper = :sk");
		query.setParameter("sk", id);
		return (List<Event>) query.getResultList();
	}
	
	/**
	 * return the given date's events
	 * 
	 * @param the action
	 */
	public List<Event> findEventsByDate(Date minDate, Date maxDate) {
		loadEm();
		Query query = em.createQuery("SELECT e FROM events e WHERE e.date >= :min AND e.date <= :max");
		query.setParameter("min", minDate);
		query.setParameter("max", maxDate);
		return (List<Event>) query.getResultList();
	}
	
	
	// Unused
	
	@Override
	public void update(Event t) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Event find(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
