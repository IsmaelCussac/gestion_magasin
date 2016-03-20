package fr.mgs.web.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.mgs.business.EventManager;
import fr.mgs.connection.DataSource;
import fr.mgs.model.event.Action;
import fr.mgs.model.event.Event;

@ManagedBean(name = "admin")
@SessionScoped
public class AdminController {
	
	private EventManager eventManager;

	@PostConstruct
	public void init() {

		eventManager = new EventManager();
		eventManager.init(DataSource.LOCAL);
	}
	
	public List<Event> getAllCreate(){
		return eventManager.findEventsByAction(Action.CREATE);
	}
	
	public List<Event> getAllVisibility(){
		List<Action> actions = new ArrayList<Action>();
		actions.add(Action.HIDE);
		actions.add(Action.SHOW);
		return eventManager.findEventsByAction(actions);
	}
	
	public List<Event> getAllUpdate(){
		List<Action> actions = new ArrayList<Action>();
		actions.add(Action.UPDATE);
		actions.add(Action.INCREASING);
		actions.add(Action.DECREASING);
		return eventManager.findEventsByAction(actions);
	}

}
