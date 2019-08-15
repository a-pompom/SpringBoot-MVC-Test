package app.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		
		return "todo";
	}
	
	@RequestMapping("save")
	private String save(TodoForm form) {
		TodoItem entity = new TodoItem();
		entity.setTask(form.getNewTask());
		
		todoDao.createTask(entity);
		
		return "redirect:/todo/init";
	}
	
	@ModelAttribute("todoForm")
	TodoForm setupForm() {
		return new TodoForm();
	}

}
