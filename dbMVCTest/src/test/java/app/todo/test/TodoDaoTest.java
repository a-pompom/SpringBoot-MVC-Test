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
	
	@BeforeEach
	void setUp() {
		// EntityManagerからDaoを作成
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
