package com.mycompany.rolesandpermissionssystem.persistence;

import com.mycompany.rolesandpermissionssystem.logic.User;

public class PersistenceController {
	
	RoleJpaController roleJpa = new RoleJpaController();
	UserJpaController userJpa = new UserJpaController();

	public void createUser(User user) {
		userJpa.create(user);
	}
}
