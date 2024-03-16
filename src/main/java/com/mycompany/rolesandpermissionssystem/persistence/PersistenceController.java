package com.mycompany.rolesandpermissionssystem.persistence;

import com.mycompany.rolesandpermissionssystem.logic.Role;
import com.mycompany.rolesandpermissionssystem.logic.User;
import com.mycompany.rolesandpermissionssystem.persistence.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenceController {
	
	RoleJpaController roleJpa = new RoleJpaController();
	UserJpaController userJpa = new UserJpaController();

	public void createUser(User user) {
		userJpa.create(user);
	}
	
	public List<User> bringUsers(){
		return userJpa.findUserEntities();
	}

	public List<Role> bringRoles() {
		return roleJpa.findRoleEntities();
	}

	public void deleteUser(int id) {
		try {
			userJpa.destroy(id);
		} catch (NonexistentEntityException ex) {
			Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
