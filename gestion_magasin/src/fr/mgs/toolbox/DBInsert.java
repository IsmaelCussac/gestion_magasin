package fr.mgs.toolbox;

import java.sql.SQLException;
import java.util.Date;

import fr.mgs.business.OrderManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.dao.OrderDAO;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.user.Privilege;
import fr.mgs.model.user.Team;
import fr.mgs.model.user.Person;

public class DBInsert {
	
	private static OrderManager orderManager;
	private static UserManager userManager;
	
	private static Order marcOrder;
	private static Order paulOrder;
	private static Order marieOrder;

	private static Team team;
	private static Team team2;

	private static Person marc;
	private static Person paul;
	private static Person marie;
	
	
	public static void fill() throws SQLException{
		userManager = new UserManager();
		userManager.init(DataSource.LOCAL);

		orderManager = new OrderManager();
		orderManager.init(DataSource.LOCAL);

		new OrderDAO(orderManager.getOrderDao().getConnection());

		team = new Team();
		team2 = new Team();

		marc = new Person();
		paul = new Person();
		marie = new Person();

		marcOrder = new Order();
		paulOrder = new Order();
		marieOrder = new Order();
		
		
		team.setTeam("100", "Approches physiques de la dynamique cellulaire et de la morphogénèse des tissus", 8,
				Privilege.CUSTOMER);
		team2.setTeam("101", "Biologie des épithéliums ciliés", 8, Privilege.CUSTOMER);

		marc.setUser("s14027276", "Marc", "Dupont", team, "0452050554", "marc.dupont@mail.fr", "pass");
		paul.setUser("s14027277", "Paul", "Durand", team, "0482060564", "paul.durand@mail.fr", "pass");
		marie.setUser("s14027278", "Marie", "Curie", team2, "0482067563", "marie.curie@mail.fr", "pass");

		marcOrder.setOrder(marc, new Date(), new Date(), null, "commande de marc", OrderStatus.NOT_VALIDATED);
		paulOrder.setOrder(paul, new Date(), new Date(), null, "commande de paul", OrderStatus.VALIDATED);
		marieOrder.setOrder(marie, new Date(), new Date(), null, "commande de marie", OrderStatus.VALIDATED);

		userManager.addTeam(team);
		userManager.addTeam(team2);

		userManager.addUser(marc);
		userManager.addUser(paul);
		userManager.addUser(marie);

		orderManager.addOrder(marcOrder);
		orderManager.addOrder(paulOrder);
		orderManager.addOrder(marieOrder);
	}
	
	public static void main(String[] args){
		try {
			fill();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
