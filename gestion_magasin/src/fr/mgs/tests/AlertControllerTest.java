package fr.mgs.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.mgs.business.EventManager;
import fr.mgs.business.OrderManager;
import fr.mgs.business.ProductManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Lot;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;
import fr.mgs.model.user.Person;
import fr.mgs.model.user.Privilege;
import fr.mgs.model.user.Team;
import fr.mgs.web.storekeeper.AlertController;
import fr.mgs.web.storekeeper.AlertProduct;

public class AlertControllerTest {
    
    private static ProductManager productManager;
    private static OrderManager orderManager;
    private static UserManager userManager;
    
    private SubCategory subCategory;
    private Product product;
    private  Order marcOrder;
    private  Order paulOrder;
    private  Order marieOrder;

    private  Team team;
    private  Team team2;

    private  Person marc;
    private  Person paul;
    private  Person marie;
    
    private Lot lot;
    
    private AlertController alert;
    
    @BeforeClass
    public static void setUpBeforeClass() throws SQLException {
        productManager = new ProductManager();
        orderManager = new OrderManager();
        userManager = new UserManager();
    }

    @AfterClass
    public static void tearDownAfterAll() {
        productManager.close();
        orderManager.close();
        userManager.close();
    }

    @Before
    public void setUp() throws SQLException {
        alert = new AlertController();
        productManager.init(DataSource.H2);
        userManager.init(DataSource.H2);
        orderManager.init(DataSource.H2);
        subCategory = new SubCategory();
        subCategory.setSubCategory("Aiguilles", Category.PLASTIC);
        productManager.addSubCategory(subCategory);

        product = new Product();
        product.setProduct(1, "Aiguille 0.4mm", subCategory, 20, 40, 4.52, true, null, 100);
        productManager.addProduct(product);
        
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
        
        lot = new Lot();
        Date date = new Date();

        lot.setLot(date, product, 15);
    }

    @Test
    public void testFindOutOfDateLots() {
        alert.findOutOfDateLots();
    }

    @Test
    public void testFindOnDemandProducts() {
        alert.findOnDemandProducts();
    }

    @Test
    public void testFindShortageStockProducts() {
        alert.findShortageStockProducts();
    }

    @Test
    public void testDaysLeft() {
        Date dateExp = new Date();
        int result = alert.daysLeft(dateExp);
    }

    @Test
    public void testGetAlerts() throws SQLException {
        List<AlertProduct> listAlert = new ArrayList<AlertProduct>();
        listAlert = alert.getAlerts();
        assertNotNull(listAlert.size());
    }


}
