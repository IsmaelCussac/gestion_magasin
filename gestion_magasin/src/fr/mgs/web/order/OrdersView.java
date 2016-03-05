package fr.mgs.web.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import fr.mgs.model.order.Order;
import fr.mgs.model.order.OrderStatus;
import fr.mgs.model.user.User;

/**
 * Manage storekeeper oders views
 * 
 * @author Ibrahima
 *
 */

@ManagedBean(name = "ordersView")
@ViewScoped
public class OrdersView {

	private List<Order> orders;
	private Order selectedOrder;

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

	public void cliquedOrderLine(Order ord) {
		selectedOrder = ord;
		System.out.println("cliquedOrderLine:---" + selectedOrder.getOrderId());
	}

	public Order getSelectedOrder() {
		System.out.println("GET:" + selectedOrder.getStatus());

		return selectedOrder;
	}

	public void setSelectedOrder(Order selectedOrder) {
		this.selectedOrder = selectedOrder;
		System.out.println("SET:" + selectedOrder.getStatus());
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Order> getOrders() {
		return orders;
	}

}