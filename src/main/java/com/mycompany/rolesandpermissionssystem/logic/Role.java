package com.mycompany.rolesandpermissionssystem.logic;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Role implements Serializable {
	
	@Id
	private int id;
	private String roleName;
	private String description;
	@OneToMany(mappedBy = "role")
	private List<User> usersList;

	public Role() {
	}

	public Role(int id, String role, String description, List<User> usersList) {
		this.id = id;
		this.roleName = role;
		this.description = description;
		this.usersList = usersList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}
	
}
