/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rolesandpermissionssystem.persistence;

import com.mycompany.rolesandpermissionssystem.logic.Role;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.rolesandpermissionssystem.logic.User;
import com.mycompany.rolesandpermissionssystem.persistence.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author charry
 */
public class RoleJpaController implements Serializable {

	public RoleJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	public RoleJpaController(){
		emf = Persistence.createEntityManagerFactory("rolesAndPermissionsJpaPU");
	}
	
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Role role) {
		if (role.getUsersList() == null) {
			role.setUsersList(new ArrayList<User>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			List<User> attachedUsersList = new ArrayList<User>();
			for (User usersListUserToAttach : role.getUsersList()) {
				usersListUserToAttach = em.getReference(usersListUserToAttach.getClass(), usersListUserToAttach.getId());
				attachedUsersList.add(usersListUserToAttach);
			}
			role.setUsersList(attachedUsersList);
			em.persist(role);
			for (User usersListUser : role.getUsersList()) {
				Role oldRoleOfUsersListUser = usersListUser.getRole();
				usersListUser.setRole(role);
				usersListUser = em.merge(usersListUser);
				if (oldRoleOfUsersListUser != null) {
					oldRoleOfUsersListUser.getUsersList().remove(usersListUser);
					oldRoleOfUsersListUser = em.merge(oldRoleOfUsersListUser);
				}
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Role role) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Role persistentRole = em.find(Role.class, role.getId());
			List<User> usersListOld = persistentRole.getUsersList();
			List<User> usersListNew = role.getUsersList();
			List<User> attachedUsersListNew = new ArrayList<User>();
			for (User usersListNewUserToAttach : usersListNew) {
				usersListNewUserToAttach = em.getReference(usersListNewUserToAttach.getClass(), usersListNewUserToAttach.getId());
				attachedUsersListNew.add(usersListNewUserToAttach);
			}
			usersListNew = attachedUsersListNew;
			role.setUsersList(usersListNew);
			role = em.merge(role);
			for (User usersListOldUser : usersListOld) {
				if (!usersListNew.contains(usersListOldUser)) {
					usersListOldUser.setRole(null);
					usersListOldUser = em.merge(usersListOldUser);
				}
			}
			for (User usersListNewUser : usersListNew) {
				if (!usersListOld.contains(usersListNewUser)) {
					Role oldRoleOfUsersListNewUser = usersListNewUser.getRole();
					usersListNewUser.setRole(role);
					usersListNewUser = em.merge(usersListNewUser);
					if (oldRoleOfUsersListNewUser != null && !oldRoleOfUsersListNewUser.equals(role)) {
						oldRoleOfUsersListNewUser.getUsersList().remove(usersListNewUser);
						oldRoleOfUsersListNewUser = em.merge(oldRoleOfUsersListNewUser);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				int id = role.getId();
				if (findRole(id) == null) {
					throw new NonexistentEntityException("The role with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(int id) throws NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Role role;
			try {
				role = em.getReference(Role.class, id);
				role.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The role with id " + id + " no longer exists.", enfe);
			}
			List<User> usersList = role.getUsersList();
			for (User usersListUser : usersList) {
				usersListUser.setRole(null);
				usersListUser = em.merge(usersListUser);
			}
			em.remove(role);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Role> findRoleEntities() {
		return findRoleEntities(true, -1, -1);
	}

	public List<Role> findRoleEntities(int maxResults, int firstResult) {
		return findRoleEntities(false, maxResults, firstResult);
	}

	private List<Role> findRoleEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Role.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public Role findRole(int id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Role.class, id);
		} finally {
			em.close();
		}
	}

	public int getRoleCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Role> rt = cq.from(Role.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
