package fr.mgs.toolbox;

import java.sql.SQLException;
import java.util.Date;

import fr.mgs.business.OrderManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.user.Person;
import fr.mgs.model.user.Privilege;
import fr.mgs.model.user.Team;
/**
 * DataBase insert
 * @author Ismael, Ibrahima
 *
 */
public class DBInsert {

	private static OrderManager orderManager;
	private static UserManager userManager;

	public static void init() throws SQLException {
		userManager = new UserManager();
		userManager.init(DataSource.LOCAL);

		orderManager = new OrderManager();
		orderManager.init(DataSource.LOCAL);

	}

	public static void insertUsers() throws SQLException {
		Person marc = new Person();
		Person paul = new Person();
		Person marie = new Person();

		Person mariana = new Person();
		Person ismael = new Person();
		Person claire = new Person();
		Person michelle = new Person();
		Person ibrahima = new Person();
		Person anthony = new Person();

		Team team = userManager.findTeam("10");
		Team team2 = userManager.findTeam("19");
		Team sk = userManager.findTeam("41");
		Team admin = userManager.findTeam("42");

		marc.setPerson("s14027276", "Marc", "Dupont", team, "0452050554",
				"marc.dupont@mail.fr", "pass");
		paul.setPerson("s14027277", "Paul", "Durand", team, "0482060564",
				"paul.durand@mail.fr", "pass");
		marie.setPerson("s14027278", "Marie", "Curie", team2, "0482067563",
				"marie.curie@mail.fr", "pass");

		mariana.setPerson("mariana", "Mariana", "Andujar", admin, "0452050554",
				"mariana.andujarf@gmail.com", "mariana");
		ismael.setPerson("ismael", "Ismaël", "Cussac", sk, "0482060564",
				"ismael.cussac@gmail.com", "ismael");
		claire.setPerson("claire", "Claire", "Gérard", team2, "0482067563",
				"claire.gerard@mail.fr", "claire");
		michelle.setPerson("michelle", "Michelle", "Kameni", team,
				"0452050554", "michelle.kameni@mail.fr", "michelle");
		ibrahima.setPerson("ibrahima", "Ibrahima", "Seye", sk, "0482060564",
				"ibrahima.seye@mail.fr", "ibrahima");
		anthony.setPerson("anthony", "Anthony", "Trieli", team2, "0482067563",
				"anthony.trieli@mail.fr", "anthony");

		userManager.addPerson(marc);
		userManager.addPerson(paul);
		userManager.addPerson(marie);
		userManager.addPerson(mariana);
		userManager.addPerson(ismael);
		userManager.addPerson(claire);
		userManager.addPerson(michelle);
		userManager.addPerson(ibrahima);
		userManager.addPerson(anthony);
	}

	public static void main(String[] args) throws SQLException {

		init();
		insertUsers();

	}

}
