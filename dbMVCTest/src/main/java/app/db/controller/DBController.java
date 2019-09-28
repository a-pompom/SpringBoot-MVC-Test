package app.db.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import app.db.dao.UserDao;
import app.db.entity.User;

/**
 * DBの基本的な処理を扱うためのコントローラ
 * 今回はDBから取得した値を単純に画面へ表示する処理のみを行う。
 * @author aoi
 *
 */
@Controller
@RequestMapping("/db")
public class DBController {
	
	//ユーザDAO
	@Autowired
	private UserDao userDao;
	
	/**
	 * 初期処理
	 * DBから取得したユーザエンティティのリストをモデルへセットし、画面へ渡す。
	 * @param model リクエストスコープへ載せるモデルパラメータ
	 * @return ホーム画面のビュー
	 */
	@RequestMapping("/init")
	private String init(Model model) {
		
		// DBからユーザテーブルの全てのレコードを取得
		List<User> userList = userDao.findAllUser();
		
		model.addAttribute("userList", userList);
		
		return "home";
	}

}
