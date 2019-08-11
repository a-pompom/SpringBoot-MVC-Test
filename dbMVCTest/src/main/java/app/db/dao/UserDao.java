package app.db.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.db.entity.User;

@Component
public class UserDao {
	
	@Autowired
	EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	public List<User> findAllUser() {
		String query = "select * from tm_user";
		
		return em.createNativeQuery(query).getResultList();
	}

}
