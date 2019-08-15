package app.db.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.db.entity.User;

/**
 * Userエンティティを操作するDaoクラス
 * @author aoi
 *
 */
@Component
public class UserDao {
	
	// EntityManagerはDIコンテナもしくはテストクラスから生成する際、
	// コンストラクタによってセットする(Autowiredではテスト時にnullとなってしまうため)
	private EntityManager em;
	
	//コンストラクタ
	public UserDao(EntityManager em) {
		this.em = em;
	}
	
	
	/**
	 * DBから全てのユーザレコードを取得する
	 * 今回はテストのため、処理を簡単なものとした。
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findAllUser() {
		
		String query = "select user_id, user_name from tm_user";
		List<User> result = em.createNativeQuery(query, User.class).getResultList();
		return result;
		//return (List<User>)em.createNativeQuery(query, User.class).getResultList();
	}
	
	@Transactional
	public void saveOrUpdate(User user) {
		
//		String query = "update tm_user set user_name = '" + user.getUserName() + "' ";
//		query += "where user_id = " + user.getUserId();
//		
//		em.createNativeQuery(query).executeUpdate();
		em.merge(user);
		em.flush();
	}
	
	@Transactional
	public void delete(int userId) {
		
		String query = "delete from tm_user where user_id = " + userId;
		
		em.createNativeQuery(query).executeUpdate();
	}
	

}
