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

	public void createUser(String fullName, String userName, String password, String roleName) {
		User user = new User();
		user.setFullName(fullName);
		user.setUserName(userName);
		user.setPassword(password);
		
		Role role = new Role();
		role = bringRole(roleName);
		
		if (role != null) {
			user.setRole(role);
		}
		
		int id = findLastId();
		user.setId(id);
		persistenceController.createUser(user);
	}

	public List<User> bringUsers() {
		return persistenceController.bringUsers();
	}

	public List<Role> bringRoles() {
		return persistenceController.bringRoles();
	}

	private Role bringRole(String roleName) {
		List<Role> listRoles = persistenceController.bringRoles();
		
		if (listRoles != null){
			for (Role role : listRoles){
				if (role.getRoleName().equals(roleName)){
					return role;
				}
			}
		}
		
		return null;
	}

	private int findLastId() {
		List<User> listUsers = persistenceController.bringUsers();
		int id ;
		
		if (!listUsers.isEmpty()){
			User user = listUsers.get(listUsers.size()-1);
			id = user.getId() + 1;
		} else {
			id = 1;
		}
		
		return id;
	}

	public void deleteUser(int id) {
		persistenceController.deleteUser(id);
	}


}
