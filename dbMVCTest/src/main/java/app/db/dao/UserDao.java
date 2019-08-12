package app.db.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;

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
		
		String query = "select * from tm_user";
		
		return em.createNativeQuery(query).getResultList();
	}

}
