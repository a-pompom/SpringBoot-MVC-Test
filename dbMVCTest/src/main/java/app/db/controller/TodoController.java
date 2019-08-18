package app.db.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.db.dao.TodoDao;
import app.db.entity.TodoItem;
import app.db.form.TodoForm;

/**
 * TODOリスト用のコントローラ
 * @author aoi
 *
 */
@Controller
@RequestMapping("/todo")
public class TodoController {
	
	@Autowired
	TodoDao todoDao;
	
	@RequestMapping("/init")
	private String init(TodoForm form) {
		List<TodoItem> todoList = todoDao.findAllTask();
		form.setTodoList(todoList);
		
		return "todo";
	}
	
	@RequestMapping("/save")
	private String save(TodoForm form) {
		TodoItem entity = new TodoItem();
		entity.setTask(form.getNewTask());
		
		todoDao.createTask(entity);
		
		return "redirect:/todo/init";
	}
	
	@RequestMapping("/update/{taskIndex}/{taskId}")
	private String update(@PathVariable("taskIndex")int taskIndex, @PathVariable("taskId")long taskId, TodoForm form) {
		TodoItem entity = form.getTodoList().get(taskIndex);
		
		entity.setTodoId(taskId);
		
		todoDao.updateTask(entity);
		
		return "redirect:/todo/init";
	}
	
	/**
	 * 画面上で指定されたタスクを削除する
	 * @param taskId 削除対象のタスクを識別するためのDI
	 * @param form フォーム
	 * @return 初期処理を呼び出して画面を再描画
	 */
	@RequestMapping("/delete/{taskId}")
	private String delete(@PathVariable("taskId")long taskId, TodoForm form) {
		
		todoDao.deleteTask(taskId);
		
		return "redirect:/todo/init";
	}
	
	@ModelAttribute("todoForm")
	TodoForm setupForm() {
		return new TodoForm();
	}

}
