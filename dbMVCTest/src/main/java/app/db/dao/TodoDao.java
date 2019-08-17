package app.db.dao;

import java.util.List;

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
	 * データベースから画面表示用に全てのタスクを取得する
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TodoItem> findAllTask() {
		String query = "select * from todo_list";
		
		return em.createNativeQuery(query, TodoItem.class).getResultList();
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
