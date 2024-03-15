package com.mycompany.rolesandpermissionssystem.persistence;

import com.mycompany.rolesandpermissionssystem.logic.User;
import java.util.List;

public class PersistenceController {
	
	RoleJpaController roleJpa = new RoleJpaController();
	UserJpaController userJpa = new UserJpaController();

	public void createUser(User user) {
		userJpa.create(user);
	}
	
	public List<User> findUsers(){
		return userJpa.findUserEntities();
	}
}
