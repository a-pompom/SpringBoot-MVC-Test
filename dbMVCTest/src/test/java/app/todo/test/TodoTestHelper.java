package app.todo.test;

import javax.persistence.EntityManager;

import app.db.entity.TodoItem;

/**
 * Todoリストでテストを行う際のヘルパークラス
 * @author aoi
 *
 */
public class TodoTestHelper {
	
	// 永続化のライフサイクルを扱うためのアノテーション
	private EntityManager em;
	
	public TodoTestHelper(EntityManager em) {
		this.em = em;
	}
	
	/**
	 * 削除・更新時に利用するIDをDBから取得する。
	 * 
	 * 自動採番されるIDは画面の情報がなければ取得することができないので、
	 * 補助的にDaoの代わりにDBへアクセスして自動採番されたIDを取得する
	 * @return 操作対象のエンティティが持つID
	 */
	public long getIdForTarget() {
		String query = "select * from todo_list where task = 'task3'";
		
		TodoItem result = (TodoItem)this.em.createNativeQuery(query, TodoItem.class).getSingleResult();
		return result.getTodoId();
	}

}
