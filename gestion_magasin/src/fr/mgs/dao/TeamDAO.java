package fr.mgs.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.persistence.TypedQuery;

import fr.mgs.connection.Connection;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.User;

/**
 * Dao used to manage team entity
 * 
 * @author Ismaël
 * 
 */
public class TeamDAO extends GenericDAO<Team,String> {

	public TeamDAO(Connection connection) {
		super.connection = connection;
	}

	/**
	 * store a team in database
	 * 
	 * @param the user to add
	 */
	public void add(Team team) throws SQLException {
		beginTransaction();
		em.persist(team);
		commit();
		closeEm();
	}

	/**
	 * remove a team stored in database using his id
	 * 
	 * @param user's id
	 */
	public void remove(String teamId) throws SQLException {
		Team team = find(teamId);
		beginTransaction();
		em.remove(em.merge(team));
		commit();
		closeEm();
	}

	/**
	 * update a team's attributes according to the fact the team is already
	 * stored in database
	 * 
	 * @param team's bean updated
	 */
	public void update(Team team) throws SQLException {
		beginTransaction();
		em.persist(em.merge(team));
		commit();
		closeEm();
	}

	/**
	 * Search if a team exists
	 * 
	 * @param team's id
	 */
	public boolean exists(String teamId) throws SQLException {
		return (find(teamId) != null);
	}

	/**
	 * find a user using his id
	 * 
	 * @param user's id
	 */
	public Team find(String teamId) throws SQLException {
		loadEm();
		Team team = em.find(Team.class, teamId);
		closeEm();
		return team;
	}

	/**
	 * return all the stored teams ordered by their name
	 */
	public Collection<Team> findAll() throws SQLException {
		loadEm();
		TypedQuery<Team> query = em.createQuery("FROM teams t order by t.name asc", Team.class);
		List<Team> result = query.getResultList();
		closeEm();
		return result;
	}

}
