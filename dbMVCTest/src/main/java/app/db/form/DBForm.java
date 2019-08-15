package app.db.form;

import java.util.List;

import app.db.entity.User;

/**
 * ユーザエンティティのリストを格納するためのフォーム
 * @author aoi
 *
 */
public class DbForm {
	
	private List<User> userList;

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	

}
