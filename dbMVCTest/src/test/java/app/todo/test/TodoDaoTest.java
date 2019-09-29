package app.todo.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import app.db.dao.TodoDao;
import app.db.entity.TodoItem;
import app.db.test.CsvDataSetLoader;
import app.test.util.DaoTestApplication;

/**
 * TODOリストのDaoのテストクラス
 * @author aoi
 *
 */
@ExtendWith(SpringExtension.class)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class
	})
@SpringBootTest(classes = {DaoTestApplication.class})
@Transactional
public class TodoDaoTest {
	
	@Autowired
	private TodoDao todoDao;
	
	/**
	 * Create処理でDBへレコードが登録されるか検証
	 */
	@Test
	@DatabaseSetup(value = "/TODO/setUp/create/")
	@ExpectedDatabase(value = "/TODO/create/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void createTaskでエンティティから新規タスクが生成される() {
		TodoItem entity = new TodoItem();
		entity.setTask("newTask");
		
		// DaoのcreateTaskメソッドを実行
		todoDao.createTask(entity);
	}
	
	/**
	 * findAllTaskメソッドでDBからレコードを正しく取得できているか検証する
	 */
	@DatabaseSetup(value = "/TODO/setUp/")
	@Test
	void findAllTaskで全てのタスクを取得できる() {
		// DaoのfindAllTaskメソッドを実行
		int actual = todoDao.findAllTask().size();
		
		// 今回は中身には関与しないので、全件取得できているかを「サイズ」から検証
		assertThat(actual, is(3));
	}
	
	/**
	 * EntityによってDBの既存レコードが更新されるか検証
	 * 既存レコードのID(Setupで生成されるもの)は自動採番されるもので特定できないので
	 * ヘルパーを経由して取得することで処理対象を明示
	 */
	@Test
	@DatabaseSetup(value = "/TODO/setUp/")
	void updateTaskで編集対象となったタスクが更新される() {
		
		// 更新対象となる既存エンティティのIDを別途取得
		
		TodoItem entity = todoDao.findByTodoId(3L);
		entity.setTask("task3mod");
		
		// DaoのupdateTaskを実行
		TodoItem actual = todoDao.updateTask(entity);
		
		assertThat(actual.getTask(), is("task3mod"));
	}
	
	/**
	 * Daoによる削除処理のテスト
	 * IDを指定することで対応したものが削除されるか検証
	 */
	@Test
	@DatabaseSetup(value = "/TODO/setUp/")
	@ExpectedDatabase(value = "/TODO/delete/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void deleteTaskで削除対象となったタスクが削除される() {
			
		// DaoのdeleteTaskを実行
		todoDao.deleteTask(3L);
	}
}
