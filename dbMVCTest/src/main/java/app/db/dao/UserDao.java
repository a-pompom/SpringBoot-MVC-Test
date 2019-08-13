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
	public User saveOrUpdate(User user) {
		return em.merge(user);
	}

}
