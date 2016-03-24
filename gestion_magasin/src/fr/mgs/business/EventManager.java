package fr.mgs.business;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.mgs.model.event.Action;
import fr.mgs.model.event.Event;
import fr.mgs.model.order.Order;

/**
 * Business class that manage the following DAO to access database and process
 * data : 
 * - Event
 * 
 * @author Ismaël
 * 
 */
public class EventManager extends Manager {

	/**
	 * store a event in database
	 * 
	 * @param event
	 *            the event to add
	 */
	public void addEvent(Event event) throws SQLException {
		beginTransaction();
		em.persist(event);
		commit();
		closeEm();
	}

	/**
	 * find an event using his id
	 * 
	 * @param eventId
	 *            event's id
	 */
	public Event findEvent(int eventId) throws SQLException {
		loadEm();
		Event event = em.find(Event.class, eventId);
		closeEm();
		return event;
	}

	/**
	 * return all the stored users ordered by their first name at first, then
	 * their name
	 */
	public Collection<Event> findAllEvents() throws SQLException {
		loadEm();
		TypedQuery<Event> query = em.createQuery("FROM events e order by e.date desc", Event.class);
		List<Event> result = query.getResultList();
		closeEm();
		return result;
	}

	/**
	 * return the given action's events
	 * 
	 * @param action
	 *            the action done
	 */
	public List<Event> findEventsByAction(Action action) {
		loadEm();
		Query query = em.createQuery("SELECT e FROM events e WHERE e.action = :a");
		query.setParameter("a", action);
		List<Event> result = query.getResultList();
		closeEm();
		return result;
	}

	/**
	 * return the given actions' events
	 * 
	 * @param actions
	 *            the actions done
	 */
	public List<Event> findEventsByAction(List<Action> actions) {
		loadEm();
		Query query = em.createQuery("SELECT e FROM events e WHERE e.action IN :a");
		query.setParameter("a", actions);
		List<Event> result = query.getResultList();
		closeEm();
		return result;
	}

	/**
	 * return the given product's events
	 * 
	 * @param productId
	 *            the product wanted
	 */
	public List<Event> findEventsByProduct(int productId) {
		loadEm();
		Query query = em.createQuery("SELECT e FROM events e WHERE e.product = :p");
		query.setParameter("p", productId);
		List<Event> result = query.getResultList();
		closeEm();
		return result;
	}

	/**
	 * return the given store keeper's events
	 * 
	 * @param storeKeeperId
	 *            the store keeper
	 */
	public List<Event> findEventsByStoreKeeper(String storeKeeperId) {
		loadEm();
		Query query = em.createQuery("SELECT e FROM events e WHERE e.storeKeeper = :sk");
		query.setParameter("sk", storeKeeperId);
		List<Event> result = query.getResultList();
		closeEm();
		return result;
	}

	/**
	 * return the given date's events
	 * 
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	public List<Event> findEventsByDate(Date minDate, Date maxDate) {
		loadEm();
		Query query = em.createQuery("SELECT e FROM events e WHERE e.date >= :min AND e.date <= :max");
		query.setParameter("min", minDate);
		query.setParameter("max", maxDate);
		List<Event> result = query.getResultList();
		closeEm();
		return result;
	}

}
