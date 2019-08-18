package app.db.form;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import app.db.entity.TodoItem;

/**
 * TODOリスト画面で扱うオブジェクトを管理するためのフォーム
 * @author aoi
 *
 */
public class TodoForm {
	
	// 新しく追加されるタスク
	@NotBlank(message = "入力してください")
	private String newTask;
	
	// 既存のタスクリスト
	@Valid
	private List<TodoItem> todoList;

	public String getNewTask() {
		return newTask;
	}

	public void setNewTask(String newTask) {
		this.newTask = newTask;
	}

	public List<TodoItem> getTodoList() {
		return todoList;
	}

	public void setTodoList(List<TodoItem> todoList) {
		this.todoList = todoList;
	}

	
	
}
