package app.db.test.crud;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
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
import app.db.test.CsvDataSetLoader;
import app.test.util.DaoTestApplication;

/**
 * Daoレイヤーのテストクラス
 * CRUD処理がDBに反映されるか検証
 * @author aoi
 *
 */
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class
	})
@SpringBootTest(classes = {DaoTestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class CRUDDaoTest {
	
	// ユーザ情報を扱うためのDAOクラス
	@Autowired
	private UserDao userDao;
	
	// select処理が正常に動作するか検証
	// 今回はただselectで取得しているだけなので、サイズから妥当性を検証する。
	@Test
	@DatabaseSetup(value = "/testData/")
	void DBからユーザ一覧を取得できる() {
		int actual = userDao.findAllUser().size();
		
		assertThat(actual, is(2));
	}
	
	/**
	 * delete処理でレコードが削除されるか検証する
	 * 処理前後のDBを用意し、削除後に想定結果となるか比較することで妥当性を検証
	 */
	@Test
	@DatabaseSetup(value = "/CRUD/setUp/")
	@ExpectedDatabase(value = "/CRUD/delete/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void deleteメソッドでユーザ1を削除できる() {
		userDao.delete(1);
	}
	
	/**
	 * update処理で既存レコードがupdateされるか検証する
	 * エンティティによってDBが想定通りに書き換えられたかExpectedDatabaseと比較することで検証
	 */
	@Test
	@DatabaseSetup(value = "/CRUD/setUp/")
	@ExpectedDatabase(value = "/CRUD/update/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void updateメソッドでユーザ1を書き換えられる() {
		User user = new User();
		user.setUserId(1L);
		user.setUserName("test1mod");
		
		userDao.saveOrUpdate(user);
	}

}
