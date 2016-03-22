package fr.mgs.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.mgs.business.OrderManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.user.Person;
import fr.mgs.model.user.Privilege;
import fr.mgs.model.user.Team;

/**
 * This class is used to test orders DAO
 * 
 * @author Ibrahima
 *
 */
public class OrderManagerTest {
    private static OrderManager orderManager;
    private static UserManager userManager;

    private  Order marcOrder;
    private  Order paulOrder;
    private  Order marieOrder;

    private  Team team;
    private  Team team2;

    private  Person marc;
    private  Person paul;
    private  Person marie;

    @BeforeClass
    public static void setUpBeforeClass() throws SQLException {
        userManager = new UserManager();

        orderManager = new OrderManager();

    }

    @AfterClass
    public static void tearDownAfterAll() throws SQLException {
        assertNotNull(orderManager);
        // test remove order
        orderManager.removeOrder(1);
        assertEquals(null, orderManager.findOrder(1));

        // closing managers
        userManager.close();
        orderManager.close();
    }

    @Before
    public void testOrderCreateOrder() throws SQLException {
        userManager.init(DataSource.H2);
        orderManager.init(DataSource.H2);
        assertNotNull(userManager);
        assertNotNull(orderManager);

        team = new Team();
        team2 = new Team();

        marc = new Person();
        paul = new Person();
        marie = new Person();

        marcOrder = new Order();
        paulOrder = new Order();
        marieOrder = new Order();

        team.setTeam("20", "Approches physiques de la dynamique cellulaire et de la morphogénèse des tissus", 8,
                Privilege.CUSTOMER);
        team2.setTeam("2", "Biologie des épithéliums ciliés", 8, Privilege.CUSTOMER);

        marc.setPerson("s14027276", "Marc", "Dupont", team, "0452050554", "marc.dupont@mail.fr", "pass");
        paul.setPerson("s14027277", "Paul", "Durand", team, "0482060564", "paul.durand@mail.fr", "pass");
        marie.setPerson("s14027278", "Marie", "Curie", team2, "0482067563", "marie.curie@mail.fr", "pass");

        marcOrder.setOrder(marc, new Date(), new Date(), null, "commande de marc", OrderStatus.NOT_VALIDATED);
        paulOrder.setOrder(paul, new Date(), new Date(), null, "commande de paul", OrderStatus.VALIDATED);
        marieOrder.setOrder(marie, new Date(), new Date(), null, "commande de marie", OrderStatus.VALIDATED);

        userManager.addTeam(team);
        userManager.addTeam(team2);

        userManager.addPerson(marc);
        userManager.addPerson(paul);
        userManager.addPerson(marie);

        orderManager.addOrder(marcOrder);
        orderManager.addOrder(paulOrder);
        orderManager.addOrder(marieOrder);
    }

    @Test
    public void testOrderFindOrder() throws SQLException {
        assertNotNull(orderManager.findOrder(marcOrder.getOrderId()));
    }
    
    @Test
    public void testOrderNotFindOrder() throws SQLException{
        Order order = new Order();
        assertNull(orderManager.findOrder(order.getOrderId()));
    }

    @Test
    public void testOrderFindAllOrders() throws SQLException {
        assertNotNull(orderManager.findAllOrders().size());

    }

    @Test
    public void testOrderByStatus() {
        assertNotNull(orderManager);
        assertEquals(2, orderManager.findOrderByStatus(OrderStatus.VALIDATED).size());
        assertEquals(1, orderManager.findOrderByStatus(OrderStatus.NOT_VALIDATED).size());

    }

    @Test
    public void testOrderByUser() {
        assertNotNull(orderManager);
        ArrayList<Order> marcOrders = (ArrayList<Order>) orderManager.findOrderByUser(marc);
        assertEquals(1, marcOrders.size());
        assertEquals("Marc", marcOrders.get(0).getOrderUser().getFirstName());

    }

    @Test
    public void testOrderByTeam() {
        assertNotNull(orderManager);
        ArrayList<Order> teamOrders = (ArrayList<Order>) orderManager.findOrderByTeam(team);
        assertEquals(2, teamOrders.size());

    }

    @Test
    public void testRemoveOrder() throws SQLException {
        orderManager.removeOrder(marcOrder.getOrderId());
        assertNull(orderManager.findOrder(marcOrder.getOrderId()));
    }

    @Test
    public void testFindAllOrdersByPerson() {
        assertNotNull(orderManager.findAllOrdersByPerson(marc));
    }

    @Test
    public void testFindAllOrdersByPersonID() {
        assertNotNull(orderManager.findAllOrdersByPerson(marc.getPersonId()));
    }

    @Test
    public void testHasValidatedOrder() {
        assertFalse(orderManager.hasNotValidatedOrder(paul.getPersonId()));
    }

    @Test
    public void testHasNotValidatedOrder() {
        assertTrue(orderManager.hasNotValidatedOrder(marc.getPersonId()));
    }

    @Test
    public void testFindNotValidatedOrder() {
        assertNotNull(orderManager.findNotValidatedOrder(marc.getPersonId()));
    }
    
    @Test
    public void testFindValidatedOrder() {
        assertTrue(orderManager.findNotValidatedOrder(paul.getPersonId()).isEmpty());
    }

}
