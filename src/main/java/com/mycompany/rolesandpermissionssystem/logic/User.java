package com.mycompany.rolesandpermissionssystem.logic;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String fullName;
	private String userName;
	private String password;
	@ManyToOne
	@JoinColumn(name="fk_idRole")
	private Role role;

	public User() {
	}

	public User(int id, String fullName, String userName, String password, Role role) {
		this.id = id;
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}
