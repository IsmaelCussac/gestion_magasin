package fr.mgs.web.customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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


@ManagedBean(name="orders")
@SessionScoped
public class OrderController {

	private OrderManager orderManager;
	private Collection<OrderLine> currentOrder;
	
	public OrderController() throws SQLException{
		orderManager = new OrderManager();
		orderManager.init(DataSource.LOCAL);
	}
	
	public void submitOrder(){
		
	}
	
	public void saveOrder(){
		
	}
	
	public void deleteOrder(){
		
	}
	
	public List<OrderLine> getCurrentOrder(){
//		User currentUser = (User) SecurityContextHolder.getContext()
//				.getAuthentication().getPrincipal();
		
		//return orderManager.findCurrentOrderLines(currentUser.getUsername());
		
		currentOrder = new ArrayList<OrderLine>();
		
		SubCategory subCategory = new SubCategory();
		subCategory.setSubCategory("Aiguilles", Category.PLASTIC);
		
		Product p1 = new Product();
		p1.setProductId(0);
		p1.setProduct("Aiguille 0.4mm", subCategory, 20, 40, 4.52, true, null, 100);
		
		Product p2 = new Product();
		p2.setProductId(1);
		p2.setProduct("Aiguille 0.2mm", subCategory, 20, 40, 4.52, true, null, 100);
		
		Team t = new Team();
		t.setTeam("100", "Approches physiques de la dynamique cellulaire et de la morphogénèse des tissus", 8,
				Privilege.CUSTOMER);
		
		Person u = new Person();
		u.setUser("s14027276", "Marc", "Dupont", t, "0452050554", "marc.dupont@mail.fr", "pass");
		
		Order o = new Order();
		o.setOrder(u, new Date(), new Date(), null, "commentaire", OrderStatus.NOT_VALIDATED);
		
		OrderLine orderLine1 = new OrderLine();
		orderLine1.setOrderLine(o, p1, 10, 0);
		
		OrderLine orderLine2 = new OrderLine();
		orderLine2.setOrderLine(o, p2, 16, 0);
		
		currentOrder.add(orderLine1);
		currentOrder.add(orderLine2);
		
		System.out.println(currentOrder);
		
		return (List<OrderLine>) currentOrder;
		

	}
}
