package com.mycompany.rolesandpermissionssystem.logic;

import com.mycompany.rolesandpermissionssystem.persistence.PersistenceController;

public class LogicController {
	
	PersistenceController persistenceController = new PersistenceController();

	public boolean checkUser(String text, String toString) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	public void createUser(String fullName, String userName, String password) {
		User user = new User();
		user.setFullName(fullName);
		user.setUserName(userName);
		user.setPassword(password);
		
		persistenceController.createUser(user);
	}
}
