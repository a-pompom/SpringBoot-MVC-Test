package app.db.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.db.entity.User;
import app.db.util.QueryBuilder;

/**
 * Userエンティティを操作するDaoクラス
 * @author aoi
 *
 */
@Component
public class UserDao extends BaseDao<User>{
	
	/**
	 * DBから全てのユーザレコードを取得する
	 * 今回はテストのため、処理を簡単なものとした。
	 * @return
	 */
	public List<User> findAllUser() {
		
		QueryBuilder query = new QueryBuilder();
		
		query.append("select user_id, user_name from tm_user");
		
		return findResultList(query.createQuery(User.class, getEm()));
	}
	
	@Transactional
	public void save(User user) {
		super.saveOrUpdate(user);
	}
	
	@Transactional
	public void delete(int userId) {
		
		QueryBuilder query = new QueryBuilder();
		query.append("delete from tm_user where user_id = :userId").setParam("userId", userId);
		
		query.createQuery(User.class, getEm()).executeUpdate();
	}
	

}
