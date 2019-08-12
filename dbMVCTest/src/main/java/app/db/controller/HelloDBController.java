package app.db.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import app.db.dao.UserDao;
import app.db.entity.User;
import app.db.form.DBForm;

@Controller
@RequestMapping("/helloDB")
public class HelloDBController {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/init")
	private String init(Model model) {
		
		List<User> userList = userDao.findAllUser();
		
		DBForm form = new DBForm();
		form.setUserList(userList);
		
		model.addAttribute("dbForm", form);
		
		return "helloDB";
	}

}
