package app.db.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.db.dao.UserDao;
import app.db.entity.User;
import app.db.form.DbForm;

/**
 * CRUD処理を行うためのコントローラ
 * @author aoi
 *
 */
@Controller
@RequestMapping("/crud")
public class DBCrudController {
	
	// ユーザ情報を操作するためのDAOクラス
	@Autowired
	UserDao userDao;
	
	/**
	 * 初期処理 DBから全てのユーザを取得し、Formへセット
	 * @param form ユーザエンティティのリストを格納したフォーム
	 * @return CRUD画面
	 */
	@RequestMapping("/init")
	private String init(DbForm form) {
		
		List<User> userList = userDao.findAllUser();
		form.setUserList(userList);
		
		return "crud";
	}
	
	/**
	 * 入力情報でDBを更新する
	 * @param index リスト内の更新対象のインデックス
	 * @param userId 更新対象のユーザID
	 * @param form 画面から渡されるフォーム
	 * @return 初期処理
	 */
	@RequestMapping("/save/{index}/{userId}")
	private String save(@PathVariable("index")int index, @PathVariable("userId")Long userId, DbForm form) {
		
		// リストから取得した結果をエンティティへ設定し、リクエストパラメーターからIDをセット
		User updateTargetUser = form.getUserList().get(index);
		updateTargetUser.setUserId(userId);
		
		userDao.saveOrUpdate(updateTargetUser);
		
		return "redirect:/crud/init";
	}
	
	@RequestMapping("/delete/{userId}")
	private String delete(@PathVariable("userId")int userId, DbForm form) {
		
		userDao.delete(userId);
		
		return "redirect:/crud/init";
	}
	
	
	@ModelAttribute("dbForm")
	DbForm setupForm() {
	    return new DbForm();
	}

}
