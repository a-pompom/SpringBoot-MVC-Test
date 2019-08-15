package app.db.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODOリスト用のコントローラ
 * @author aoi
 *
 */
@Controller
@RequestMapping("/todo")
public class TodoController {
	
	@RequestMapping("/init")
	private String init() {
		
		return "todo";
	}

}
