package fr.mgs.web.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.mgs.business.EventManager;
import fr.mgs.business.UserManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.event.Action;
import fr.mgs.model.event.Event;

/**
 * Bean used to manage the admin view
 * 
 * @author Ismaël
 *
 */
@ManagedBean(name = "admin")
@SessionScoped
public class AdminController {
	
	private EventManager eventManager;
	private UserManager userManager;

	@PostConstruct
	public void init() {

		eventManager = new EventManager();
		eventManager.init(DataSource.LOCAL);
		
		userManager = new UserManager();
		userManager.init(DataSource.LOCAL);
	}
	
	/**
	 * 
	 * @return a list of all creation actions on products
	 */
	public List<Event> getAllCreate(){
		return eventManager.findEventsByAction(Action.CREATE);
	}
	
	/**
	 * 
	 * @return a list of all visibility modification actions on products
	 */
	public List<Event> getAllVisibility(){
		List<Action> actions = new ArrayList<Action>();
		actions.add(Action.HIDE);
		actions.add(Action.SHOW);
		return eventManager.findEventsByAction(actions);
	}
	
	/**
	 * 
	 * @return a list of all modification actions on products
	 */
	public List<Event> getAllUpdate(){
		return eventManager.findEventsByAction(Action.UPDATE);
	}

}
