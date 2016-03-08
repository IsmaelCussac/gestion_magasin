package fr.mgs.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.primefaces.component.calendar.Calendar;

import fr.mgs.business.EventManager;
import fr.mgs.business.ProductManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.event.Action;
import fr.mgs.model.event.Event;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;


/**
 * This class is used to test orders DAO
 * 
 * @author Anthony
 *
 */
public class EventDaoTest {
	private static EventManager eventManager;
	private static ProductManager productManager;
	private Event E1;
	private Action A1;
	private Product product;
	private SubCategory subCategory;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws SQLException {
		eventManager = new EventManager();
		productManager = new ProductManager();
	}

	@AfterClass
	public static void tearDownAfterAll() {
		productManager.getDaoManager().close();
		eventManager.getDaoManager().close();
	}
	
	
	@Before
	public void setUp() throws SQLException {
		eventManager.init(DataSource.H2);
		
		subCategory = new SubCategory();
		subCategory.setSubCategory("Aiguilles", Category.PLASTIC);
		productManager.addSubCategory(subCategory);
		
		product = new Product();
		product.setProduct("Aiguille 0.4mm", subCategory, 20, 40, 4.52, true, null, 100);
		
	}
	
	/*
	 * 
	 * 	Test sur la création d'une ligne de log
	 * 
	 */
	@Test
	public void testEventCreate() throws SQLException {
		
		E1 = new Event();
		Date d1 = new Date();
		E1.setHistorical("123", product, Action.INCREASING , d1, "test");
		eventManager.addLogMonitor(E1);
		assertTrue(eventManager.findAllLogMonitors().size() == 1);
	}
	
	/*
	 * 
	 * 	Test sur la recherche d'une ligne de log
	 * 
	 */
	@Test
	public void testEventFind() throws SQLException {
		
		// on ajoute une ligne 
		E1 = new Event();
		Date d1 = new Date();
		E1.setHistorical("123", product, Action.INCREASING , d1, "test");
		eventManager.addLogMonitor(E1);
		
		// on recup les lignes de log 
		Collection<Event> ListE = eventManager.findAllLogMonitors();
		int id = ListE.iterator().next().getLogMonitorId();
		
		// on recherche un element présent en base par son id
		Event E2 = eventManager.findLogMonitor(id);
		
		// on verifie que la ligne renvoyé est bien celle recherché
		assertTrue(E2.getResume().equals(E1.getResume()));
	}
	
	/*
	 * 
	 * 	Test sur la recherche d'une ligne de log
	 * 
	 */
	@Test
	public void testEventFindAll() throws SQLException {
		
		// on ajoute une ligne 
		E1 = new Event();
		Date d1 = new Date();
		E1.setHistorical("123", product, Action.INCREASING , d1, "test");
		eventManager.addLogMonitor(E1);
		eventManager.addLogMonitor(E1);
		
		// on recup les lignes de log 
		int taille = eventManager.findAllLogMonitors().size();
		
		assertTrue(taille>=2);
	}
	
	
	/*
	 * 
	 * 	Test sur la supression d'une ligne de log
	 * 
	 */
	@Test
	public void testEventRemove() throws SQLException {
		
		// on ajoute une ligne 
		E1 = new Event();
		Date d1 = new Date();
		E1.setHistorical("123", product, Action.INCREASING , d1, "test");
		eventManager.addLogMonitor(E1);
		
		// on recup la taille
		int taille_avt = eventManager.findAllLogMonitors().size();
		
		// on recup un des elements de la base (id)
		Collection<Event> ListE = eventManager.findAllLogMonitors();
		int id = ListE.iterator().next().getLogMonitorId();
		
		// on supprime
		eventManager.removeLogMonitor(id);
		
		// on verifie que le nombre d'élement a bien changer
		int taille_aprs = eventManager.findAllLogMonitors().size();
		
		assertTrue(taille_avt != taille_aprs);
	}
	
	
	/*
	 * 
	 * 	Test sur la modif d'une ligne de log
	 * 
	 */
	@Test
	public void testUpdateEvent() throws SQLException {
		
		// on ajoute une ligne 
		E1 = new Event();
		Date d1 = new Date();
		E1.setHistorical("123", product, Action.INCREASING , d1, "test");
		eventManager.addLogMonitor(E1);
		
		// on modifie une donnée
		E1.setResume("test2");
		eventManager.updateLogMonitor(E1);
		
		// on recup la liste des log 
		Collection<Event> ListE = eventManager.findAllLogMonitors();
		
		String resume = "";
		while ( ListE.iterator().hasNext() == true){
			ListE.iterator().next().getLogMonitorId();
		}
		
		// On compare la modif à l original
		assertTrue(!E1.getResume().equals(resume));
	}
	
}
