package fr.mgs.web.customer;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import fr.mgs.business.OrderManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.order.Order;

@ManagedBean(name = "cstOrderHistory")
@SessionScoped
public class OrderHistoryController {
	
	private OrderManager orderManager;
	private String userId;
	
	@PostConstruct
	public void init(){
		orderManager = new OrderManager();
		try {
			orderManager.init(DataSource.LOCAL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userId = user.getUsername();
		
	}
	
	public List<Order> getOrders() throws SQLException{
		
		return (List<Order>) orderManager.findAllOrdersByUser(userId);
		
	}
	
	public String duplicateOrder(Order order){
		
		return "pretty:cstOrder";
	}
	
	
}
