package app.db.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.db.entity.TodoItem;

/**
 * Todoリスト用のテーブルを操作するためのDaoクラス
 * @author aoi
 *
 */
@Component
public class TodoDao {
	
	private EntityManager em;
	
	public TodoDao(EntityManager em) {
		this.em = em;
	}
	
	/**
	 * フォームの入力値によって得られたEntityをDBへ新規登録する
	 * @param task
	 */
	@Transactional
	public void createTask(TodoItem task) {
		em.persist(task);
		
		em.flush();
	}

}
