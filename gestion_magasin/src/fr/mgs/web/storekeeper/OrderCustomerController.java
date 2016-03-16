package fr.mgs.web.storekeeper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import fr.mgs.business.OrderManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderLine;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.product.Category;
import fr.mgs.model.product.Product;
import fr.mgs.model.product.SubCategory;
import fr.mgs.model.user.Person;
import fr.mgs.model.user.Privilege;
import fr.mgs.model.user.Team;

@ManagedBean(name = "myOrders")
@SessionScoped
public class OrderCustomerController {

	private OrderManager orderManager;
	private Order theOrder;
	private Collection<Order> orders;
	private Collection<OrderLine> orderLines;

	public OrderCustomerController() throws SQLException {
		orderManager = new OrderManager();
		orderManager.init(DataSource.LOCAL);

		theOrder = new Order();
		orders = new ArrayList<Order>();
		orderLines = new ArrayList<OrderLine>();
	}

	public Collection<OrderLine> getOrderLines() {

		Team t = new Team();
		t.setTeam("1", "team 1", 6, Privilege.CUSTOMER);

		// User u = new User();
		Person pers = new Person();
		pers.setPerson("a11111", "Guillaume", "Thibault", t, "0491111111", "guillaume.thibault@mail.fr", "azerty");

		SubCategory subCategory = new SubCategory();
		subCategory.setSubCategory("Stylo", Category.PAPER);

		Product p1 = new Product();
		p1.setProductId(1);
		p1.setProduct("Stylo Bleu", subCategory, 0, 20, 1.15, true, null, 100);

		Product p2 = new Product();
		p2.setProductId(2);
		p2.setProduct("Stylo Noir", subCategory, 0, 20, 1.15, true, null, 100);

		Order ord = new Order();
		ord.setOrder(pers, new Date(), new Date(), null, "", OrderStatus.NOT_VALIDATED);

		OrderLine orderLine1 = new OrderLine();
		orderLine1.setOrderLine(ord, p1, 10, 0);

		OrderLine orderLine2 = new OrderLine();
		orderLine2.setOrderLine(ord, p2, 5, 0);

		orderLines.add(orderLine1);
		orderLines.add(orderLine2);

		return (List<OrderLine>) orderLines;
	}

	public List<Order> showOrders(Person p) { // p = theConnectedUser
		orders = orderManager.findAllOrdersByPerson(p);
		return (List<Order>) orders;
	}

	public Order getTheOrder(Person p) { // p = theConnectedUser
		orders = orderManager.findAllOrdersByPerson(p);
		for (Order ord : orders) {
			if (ord.getStatus() == OrderStatus.NOT_VALIDATED) {
				theOrder = ord;
			}
			theOrder = null;
		}
		return theOrder;
	}

	public void modifyOrder() throws SQLException {
		orderManager.updateOrder(theOrder);
	}

	public void saveOrder() throws SQLException {
		orderManager.addOrder(theOrder);
	}

	public void validateOrder() throws SQLException {
		theOrder.setStatus(OrderStatus.VALIDATED);
		orderManager.updateOrder(theOrder);
	}

	public void removeOrderline() throws SQLException {
		for (OrderLine ol : orderLines) {
			if (ol.getQuantity() == 0) {
				orderManager.removeOrderLine(ol.getOrderLinePK());
			}
		}
		orderManager.updateOrder(theOrder);
	}

	public Order duplicateOrder() throws SQLException {
		// r�cup�ration de la commande se�lectionner dans theOrder
		// theOrder = selectedOrder;

		// A faire dans le manager ?
		Order ord = orderManager.findOrder(theOrder.getOrderId());
		Order duplicatedOrder = new Order();
		duplicatedOrder.setStatus(OrderStatus.NOT_VALIDATED);
		duplicatedOrder.setOrderUser(ord.getOrderUser());
		duplicatedOrder.setOrderLines(ord.getOrderLines());
		return duplicatedOrder;
	}

}
