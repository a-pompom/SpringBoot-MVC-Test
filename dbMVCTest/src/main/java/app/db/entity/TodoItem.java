package app.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Todoリストの各タスクを格納するためのEntity
 * @author aoi
 *
 */
@Entity
@Table(name = "todo_list")
public class TodoItem {
	
	private Long todoId;
	
	private String task;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "todo_id")
	public Long getTodoId() {
		return todoId;
	}

	public void setTodoId(Long todoId) {
		this.todoId = todoId;
	}

	@Column(name = "task")
	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}
	
	

}
