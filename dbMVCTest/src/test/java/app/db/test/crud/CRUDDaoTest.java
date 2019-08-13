package app.db.test.crud;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

import app.db.dao.UserDao;
import app.db.entity.User;
import app.db.main.DbMvcTestApplication;
import app.db.test.CsvDataSetLoader;

/**
 * Daoレイヤーのテストクラス
 * CRUD処理がDBに反映されるか検証
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
public class CRUDDaoTest {
	
	// 永続化のライフサイクルを扱うためのアノテーション
	@PersistenceContext
	private EntityManager em;
	
	// ユーザ情報を扱うためのDAOクラス
	private UserDao userDao;
	
	@BeforeEach
	void setUp() {
		this.userDao = new UserDao(em);
	}
	
	@Test
	@DatabaseSetup(value = "/testData/")
	void DBからユーザ一覧を取得できる() {
		int actual = userDao.findAllUser().size();
		
		assertThat(actual, is(2));
	}
	
	@Test
	@DatabaseSetup(value = "/CRUD/setUp/")
	@ExpectedDatabase(value = "/CRUD/delete/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void deleteメソッドでユーザ1を削除できる() {
		userDao.delete(1);
	}
	
	@Test
	@DatabaseSetup(value = "/CRUD/setUp/")
	@ExpectedDatabase(value = "/CRUD/update/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void updateメソッドでユーザ1を書き換えられる() {
		User user = new User();
		user.setUserId(1L);
		user.setUserName("test1mod");
		
		userDao.saveOrUpdate(user);
		
		// 永続コンテキストとDBの同期をとることで
		// UPDATE結果のレコード群と比較が可能となる
		this.em.flush();
	}

}
