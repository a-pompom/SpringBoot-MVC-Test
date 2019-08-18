package app.db.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	
	/**
	 * 初期処理
	 * DBからタスクを全て取得し、フォームへセットすることで画面での一覧表示を可能とする
	 * @param form todoList及び新規入力を格納するためのフォーム
	 * @return todoListを表示するためのview
	 */
	@RequestMapping("/init")
	private String init(TodoForm form) {
		List<TodoItem> todoList = todoDao.findAllTask();
		form.setTodoList(todoList);
		
		return "todo";
	}
	
	/**
	 * フォームの入力をもとにDBへ新規レコードを登録する
	 * @param form 新規レコード用の入力を格納したフォーム
	 * @return 新規レコードをリストへ追加した後に再描画
	 */
	@RequestMapping("/save")
	private String save(@Valid TodoForm form, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";
		}
		
		TodoItem entity = new TodoItem();
		entity.setTask(form.getNewTask());
		
		todoDao.createTask(entity);
		
		return "redirect:/todo/inito";
	}
	
	/**
	 * 画面上で指定されたタスクを更新する
	 * @param taskIndex リスト内のタスクのインデックス listからgetメソッドを呼び出す際の引数として利用
	 * @param taskId 自動採番されるタスクのID
	 * @param form
	 * @return リストへ結果を反映させるために再描画
	 */
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
