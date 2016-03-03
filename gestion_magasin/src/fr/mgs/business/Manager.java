package fr.mgs.business;

import javax.annotation.PostConstruct;

import fr.mgs.dao.Connection;

public abstract class Manager {
	
	private Connection connection;
	
	public Manager(){}
	
	@PostConstruct
	public void init(){
		connection = new Connection();
		connection.initEmf();
	}
	
	

}
