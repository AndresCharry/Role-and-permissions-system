package com.mycompany.rolesandpermissionssystem.logic;

import com.mycompany.rolesandpermissionssystem.persistence.PersistenceController;
import java.util.List;

public class LogicController {
	
	PersistenceController persistenceController = new PersistenceController();

	public User checkUser(String userName, String password) {
		List<User> listUsers = persistenceController.bringUsers();
		
		if (!listUsers.isEmpty()){
			for (User user : listUsers){
				if (user.getUserName().equalsIgnoreCase(userName)){
					if(user.getPassword().equals(password)){
						return user;
					}
				}
			}
		}
		return null;
	}

	public void createUser(String fullName, String userName, String password) {
		User user = new User();
		user.setFullName(fullName);
		user.setUserName(userName);
		user.setPassword(password);
		
		persistenceController.createUser(user);
	}

	public List<User> bringUsers() {
		return persistenceController.bringUsers();
	}


}
