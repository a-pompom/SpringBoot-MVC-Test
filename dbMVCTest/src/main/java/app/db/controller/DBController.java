package app.db.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import app.db.dao.UserDao;
import app.db.entity.User;

@Controller
@RequestMapping("/db")
public class DBController {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/init")
	private String init(Model model) {
		
		List<User> userList = userDao.findAllUser();
		
		model.addAttribute("userList", userList);
		
		return "home";
	}

}
