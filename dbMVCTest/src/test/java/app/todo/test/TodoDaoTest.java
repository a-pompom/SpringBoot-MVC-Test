package app.todo.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import app.db.main.DbMvcTestApplication;
import app.db.test.CsvDataSetLoader;

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
@SpringBootTest(classes = {DbMvcTestApplication.class})
@Transactional
public class TodoDaoTest {
	
	// 永続化のライフサイクルを扱うためのアノテーション
	@PersistenceContext
	private EntityManager em;
	
	private TodoDao todoDao;
	
	private TodoTestHelper helper;
	
	@BeforeEach
	void setUp() {
		// EntityManagerからDaoを作成
		this.helper = new TodoTestHelper(em);
		this.todoDao = new TodoDao(em);
		
	}
	
	@Test
	@DatabaseSetup(value = "/TODO/setUp/")
	@ExpectedDatabase(value = "/TODO/create/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void createTaskでエンティティから新規タスクが生成される() {
		TodoItem entity = new TodoItem();
		entity.setTask("newTask");
		
		// DaoのcreateTaskメソッドを実行
		todoDao.createTask(entity);
		
		// 実行後のDBに期待結果DBと同様の新規タスクレコードが追加されていること
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
	
	@Test
	@DatabaseSetup(value = "/TODO/setUp/")
	@ExpectedDatabase(value = "/TODO/update/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void updateTaskで編集対象となったタスクが更新される() {
		
		long updateTargetId = helper.getIdForTarget();
		TodoItem entity = new TodoItem();
		entity.setTodoId(updateTargetId);
		entity.setTask("task3mod");
		
		// DaoのupdateTaskを実行
		todoDao.updateTask(entity);
				 
	}
	
	/**
	 * Daoによる削除処理のテスト
	 * IDを指定することで対応したものが削除されるか検証
	 */
	@Test
	@DatabaseSetup(value = "/TODO/setUp/")
	@ExpectedDatabase(value = "/TODO/delete/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void deleteTaskで削除対象となったタスクが削除される() {
		
		// IDは自動で採番されるものなので、
		// 画面と同様DBから取得した結果をループで回し、削除対象のものについて、IDを取得する
		long deleteTargetId = helper.getIdForTarget();
		
		
		// DaoのdeleteTaskを実行
		todoDao.deleteTask(deleteTargetId);
	}
	
	

}
