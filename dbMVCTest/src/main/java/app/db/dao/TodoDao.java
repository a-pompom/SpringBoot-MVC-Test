package app.db.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.db.entity.TodoItem;
import app.db.util.QueryBuilder;

/**
 * Todoリスト用のテーブルを操作するためのDaoクラス
 * @author aoi
 *
 */
@Component
public class TodoDao extends BaseDao<TodoItem>{
	
	/**
	 * データベースから画面表示用に全てのタスクを取得する
	 * @return
	 */
	public List<TodoItem> findAllTask() {
		QueryBuilder query = new QueryBuilder();
		query.append("select * from todo_list");
		
		return findResultList(query.createQuery(TodoItem.class, getEm()));
	}
	
	/**
	 * フォームの入力値によって得られたEntityをDBへ新規登録する
	 * @param task
	 */
	@Transactional
	public TodoItem createTask(TodoItem task) {
		return saveOrUpdate(task);
	}
	
	/**
	 * 指定されたタスクを編集する
	 * @param task 更新対象のエンティティ
	 */
	@Transactional
	public TodoItem updateTask(TodoItem task) {
		return saveOrUpdate(task);
	}
	
	/**
	 * 指定されたタスクを削除する
	 * @param taskId 削除対象を識別するためのID
	 */
	@Transactional
	public void deleteTask(long taskId) {
		QueryBuilder query = new QueryBuilder();
		query.append("delete from todo_list ");
		query.append("where todo_id = :taskId").setParam("taskId", taskId);
		
		query.createQuery(TodoItem.class, getEm()).executeUpdate();
	}
	
	public TodoItem findByTodoId(long taskId) {
		QueryBuilder query = new QueryBuilder();
		
		query.append("select * from todo_list ");
		query.append("where todo_id = :taskId").setParam("taskId", taskId);
		
		return findSingle(query.createQuery(TodoItem.class, getEm()));
	}

}
