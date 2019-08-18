package app.todo.test;

import javax.persistence.EntityManager;

import app.db.entity.TodoItem;

public class TodoTestHelper {
	
	// 永続化のライフサイクルを扱うためのアノテーション
	private EntityManager em;
	
	public TodoTestHelper(EntityManager em) {
		this.em = em;
	}
	
	public long getIdForTarget() {
		String query = "select * from todo_list where task = 'task3'";
		
		TodoItem result = (TodoItem)this.em.createNativeQuery(query, TodoItem.class).getSingleResult();
		return result.getTodoId();
	}

}
