package app.db.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * リクエストの正しさを検証する際に利用するコントローラ
 * モデルにいくつか値を設定し、ビューを返すのみのシンプルな処理を行う
 * @author aoi
 *
 */
@Controller
@RequestMapping("/hello")
public class HelloController {
	
	@RequestMapping("/init")
	private String init(Model model) {
		
		model.addAttribute("message", "hello!");
		
		return "hello";
	}

}
