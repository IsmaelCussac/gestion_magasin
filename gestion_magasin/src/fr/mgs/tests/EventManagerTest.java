package fr.mgs.tests;

import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.mgs.business.EventManager;
import fr.mgs.business.ProductManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.event.Action;
import fr.mgs.model.event.Event;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;
import fr.mgs.model.user.Person;
import fr.mgs.model.user.Privilege;
import fr.mgs.model.user.Team;

/**
 * This class is used to test orders DAO
 * 
 * @author Anthony A FAIRE
 */
public class EventManagerTest {
	private static EventManager eventManager;
	private static ProductManager productManager;
	private Event e1;
	private Product product;
	private SubCategory subCategory;
	private Date d1;
	private Team team;
	private Person person;
	private static UserManager userManager;

	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		eventManager = new EventManager();
		productManager = new ProductManager();
		userManager = new UserManager();
	}

	@AfterClass
	public static void tearDownAfterAll() {
		productManager.close();
		eventManager.close();
		userManager.close();
	}

	@Before
	public void setUp() throws SQLException {
		eventManager.init(DataSource.H2);
		productManager.init(DataSource.H2);
		userManager.init(DataSource.H2);
		
		team = new Team();
        team.setTeam("APDCMT", "Approches physiques de la dynamique cellulaire et de la morphogénèse des tissus", 7,
                Privilege.CUSTOMER);
        userManager.addTeam(team);

        person = new Person();
        person.setPerson("d1102526", "Jean-Louis", "De Beauregard", team, "0442060504",
                "jean-louis.de-beauregard@mail.fr", "secret");
        userManager.addPerson(person);

		subCategory = new SubCategory();
		subCategory.setSubCategory("Aiguilles", Category.PLASTIC);

		product = new Product();
		product.setProduct(1, "Aiguille 0.4mm", subCategory, 20, 40, 4.52, true, null, 100);

		e1 = new Event();
		d1 = new Date();
		e1.setEvent("123", product, Action.INCREASING, d1, "test");
	}

	/*
	 * 
	 * Test sur la cr�ation d'une ligne de log
	 * 
	 */
	@Test
	public void testEventCreate() throws SQLException {
		eventManager.addEvent(e1);
		assertNotNull(eventManager.findAllEvents());
	}

	/*
	 * 
	 * Test sur la recherche d'une ligne de log
	 * 
	 */
	@Test
	public void testEventFind() throws SQLException {
		eventManager.addEvent(e1);
		assertNotNull(eventManager.findEvent(e1.getEventId()));
	}

	/*
	 * 
	 * Test sur la recherche d'une ligne de log
	 * 
	 */
	@Test
	public void testEventFindAll() throws SQLException {
		eventManager.addEvent(e1);
		Collection<Event> collect = eventManager.findAllEvents();
		assertNotNull(collect.size());
	}
	
	@Test
	public void testFindEventsByAction(){
	    assertNotNull(eventManager.findEventsByAction(Action.INCREASING));
	}
	
	@Test
    public void testFindEventsByProduct(){
        assertNotNull(eventManager.findEventsByProduct(product.getProductId()));
    }
	
	@Test
	public void testFindEventsByStoreKeeper(){
	    assertNotNull(eventManager.findEventsByStoreKeeper(person.getPersonId()));
	}
	
	@Test
	public void testFindEventsByDate(){
	    Date date1 = new Date();
	    Date date2 = new Date();
	    assertNotNull(eventManager.findEventsByDate(date1, date2));
	}

}
