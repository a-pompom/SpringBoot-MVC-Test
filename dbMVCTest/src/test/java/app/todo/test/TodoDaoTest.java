package app.todo.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TODOリストのDaoのテストクラス
 * @author aoi
 *
 */
public class TodoDaoTest {
	
	@BeforeEach
	void setUp() {
		// EntityManagerからDaoを作成
	}
	
	@Test
	void createTaskでエンティティから新規タスクが生成される() {
		// DaoのcreateTaskメソッドを実行
		// このとき、タスクEntityを引数として渡す
		
		// 実行後のDBに期待結果DBと同様の新規タスクレコードが追加されていること
	}
	
	@Test
	void findAllTaskで全てのタスクを取得できる() {
		// DaoのfindAllTaskメソッドを実行
		
		// 今回は中身には関与しないので、全件取得できているかを「サイズ」から検証
	}
	
	@Test
	void updateTaskで編集対象となったタスクが更新される() {
		// DaoのupdateTaskを実行
		
		// update処理を行った後に期待結果DBと結果が一致するかを検証 
	}
	
	@Test
	void deleteTaskで削除対象となったタスクが削除される() {
		// DaoのdeleteTaskを実行
		
		// delete後の処理結果が期待結果と一致するかを検証
	}
	
	

}
