package fr.mgs.web.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.user.User;

/**
 * Manage storekeeper oders views
 * 
 * @author Ibrahima
 *
 */

@ManagedBean
@ViewScoped
public class OrdersView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Order> orders;
	private Order selectedOrder;

	private DataModel<Order> od;

	@PostConstruct
	public void init() {

		orders = new ArrayList<Order>();
		selectedOrder = new Order();
		User u1 = new User();
		u1.setUserId("Cl000");

		User u2 = new User();
		u1.setUserId("Cl111");

		Order order1 = new Order();
		order1.setOrderId(22222);
		order1.setStatus(OrderStatus.VALIDATED);
		order1.setSubmissionDate(new Date());
		order1.setOrderUser(u1);

		Order order2 = new Order();
		order2.setOrderId(11111);
		order2.setStatus(OrderStatus.VALIDATED);
		order2.setSubmissionDate(new Date());
		order2.setOrderUser(u2);

		orders.add(order1);
		orders.add(order2);
	}

	public DataModel<Order> getOd() {
		List<Order> ords = new ArrayList<Order>();
		User u1 = new User();
		u1.setUserId("Cl000");

		User u2 = new User();
		u1.setUserId("Cl111");

		Order order1 = new Order();
		order1.setOrderId(22222);
		order1.setStatus(OrderStatus.VALIDATED);
		order1.setSubmissionDate(new Date());
		order1.setOrderUser(u1);

		Order order2 = new Order();
		order2.setOrderId(11111);
		order2.setStatus(OrderStatus.VALIDATED);
		order2.setSubmissionDate(new Date());
		order2.setOrderUser(u2);

		ords.add(order1);
		ords.add(order2);

		od = new ListDataModel<Order>(ords);

		setOd(od);

		return od;
	}

	public void setOd(DataModel<Order> od) {
		this.od = od;
	}

	public String editOrder() {

		selectedOrder = (Order) od.getRowData();
		return "delivery";
	}

	public void ligneOrderCliqued(Order ord) {
		selectedOrder = ord;
	}

	public void show() {
		System.out.println(selectedOrder.getOrderId());
	}

	public Order getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(Order selectedOrder) {
		this.selectedOrder = selectedOrder;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Order> getOrders() {
		return orders;
	}

}