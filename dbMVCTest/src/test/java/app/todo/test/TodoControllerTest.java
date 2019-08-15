package app.todo.test;

import org.junit.jupiter.api.Test;

/**
 * TODOリストのコントローラのテストクラス
 * @author aoi
 *
 */
public class TodoControllerTest {
	
	@Test
	void init処理で既存のタスクがモデルへ渡される() {
		
		// mockMvcで「/todo/init」へgetリクエストを送信
		
		// ビューとして「todo」が渡され、
		// モデルへDBのレコードがリストとして渡される
	}
	
	@Test
	void save処理で新規タスクがDBへ登録される() {
		
		// mockMvcで「todo/save」へpostリクエストを送信
		
		// 画面の入力値をもとにDBへ新規レコードが登録される
		
	}
	
	@Test
	void update処理で既存タスクが更新される() {
		
		// mockMvcで「todo/update」へpostリクエストを送信
		
		// 画面で選択されたタスクと対応したDBの既存レコードが更新される
		
	}
	
	@Test
	void delete処理で既存タスクが消去される() {
		
		// mockMvcで「todo/delete」へpostリクエストを送信
		
		// 画面で選択されたタスクと対応したDBの既存レコードが削除される
	}

}
