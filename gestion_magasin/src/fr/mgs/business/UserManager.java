package fr.mgs.business;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.mgs.model.user.Person;
import fr.mgs.model.user.Team;

/**
 * Business class that manage the following DAOs to access database and process
 * data : 
 * - User 
 * - Team
 * 
 * @author Ismaël
 * @author Ibrahima
 *
 */
public class UserManager extends Manager {

	// TEAM

	/**
	 * store a team in database
	 * 
	 * @param team
	 *            the team to add
	 */
	public void addTeam(Team team) throws SQLException {
		beginTransaction();
		em.persist(team);
		commit();
		closeEm();
	}

	/**
	 * remove a team stored in database using his id
	 * 
	 * @param teamId
	 *            team's id
	 */
	public void removeTeam(String teamId) throws SQLException {
		Team team = findTeam(teamId);
		beginTransaction();
		em.remove(em.merge(team));
		commit();
		closeEm();
	}

	/**
	 * update a team's attributes according to the fact the team is already
	 * stored in database
	 * 
	 * @param team
	 *            team's bean updated
	 */
	public void updateTeam(Team team) throws SQLException {
		beginTransaction();
		em.persist(em.merge(team));
		commit();
		closeEm();
	}

	/**
	 * Search if a team exists
	 * 
	 * @param teamId
	 *            team's id
	 */
	public boolean teamExists(String teamId) throws SQLException {
		return findTeam(teamId) != null;
	}

	/**
	 * find a team using his id
	 * 
	 * @param teamId
	 *            team's id
	 */
	public Team findTeam(String teamId) throws SQLException {
		loadEm();
		Team team = em.find(Team.class, teamId);
		closeEm();
		return team;
	}

	/**
	 * return all the stored teams ordered by their name
	 */
	public Collection<Team> findAllTeams() throws SQLException {
		loadEm();
		TypedQuery<Team> query = em.createQuery("FROM teams t order by t.name asc", Team.class);
		List<Team> result = query.getResultList();
		closeEm();
		return result;
	}

	// PERSON

	/**
	 * store a person in database
	 * 
	 * @param person
	 *            the person to add
	 */
	public void addPerson(Person person) throws SQLException {
		beginTransaction();
		em.persist(person);
		commit();
		closeEm();
	}

	/**
	 * remove a person stored in database using his id
	 * 
	 * @param person
	 *            person's id
	 */
	public void removePerson(String personId) throws SQLException {
		Person person = findPerson(personId);
		beginTransaction();
		em.remove(em.merge(person));
		commit();
		closeEm();
	}

	/**
	 * update a person's attributes according to the fact the person is already
	 * stored in database
	 * 
	 * @param person
	 *            person's bean updated
	 */
	public void updatePerson(Person person) throws SQLException {
		beginTransaction();
		em.persist(em.merge(person));
		commit();
		closeEm();
	}

	/**
	 * Search if a person exists
	 * 
	 * @param personId
	 *            person's id
	 */
	public boolean personExists(String personId) throws SQLException {
		return findPerson(personId) != null;
	}

	/**
	 * find a person using his id
	 * 
	 * @param personId
	 *            person's id
	 */
	public Person findPerson(String personId) throws SQLException {
		loadEm();
		Person person = em.find(Person.class, personId);
		closeEm();
		return person;
	}

	/**
	 * return all the stored persons ordered by their first name at first, then
	 * their name
	 */
	public Collection<Person> findAllPersons() throws SQLException {
		loadEm();
		TypedQuery<Person> query = em.createQuery("FROM users u order by u.firstName, u.lastName asc", Person.class);
		List<Person> result = query.getResultList();
		closeEm();
		return result;
	}

	/**
	 * return the given team's persons
	 * 
	 * @param team
	 *            the team
	 */
	public List<Person> findPersonsByTeam(Team team) {
		loadEm();
		Query query = em.createQuery("SELECT u FROM users u WHERE u.team = :ot");
		query.setParameter("ot", team);
		return (List<Person>) query.getResultList();
	}
}
