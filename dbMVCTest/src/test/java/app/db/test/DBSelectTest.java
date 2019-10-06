package app.db.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import app.db.dao.UserDao;
import app.db.entity.User;
import app.test.util.DaoTestApplication;

/**
 * DBからテーブルデータを取得するテストを行うためのテストクラス
 * @ExtendWith...JUnit5でテストクラスを扱うためのアノテーション
 * @DbUnitConfiguration...XMLからCSVを読み込むための設定を取得
 * @TestExecutionListeners...TestContextへ設定する前処理を登録するためのもの
 * @SpringBootTest...SpringBootのアプリとしてテストを行うためのアノテーション application.propertiesなどの設定を利用するために設定
 * 
 * @author aoi
 *
 */
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class
	})
@SpringBootTest(classes = {DaoTestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DBSelectTest {
	
	@Autowired
	private UserDao userDao;
	
	// DatabaseSetupのvalueにCSVファイルのパスを設定することで、「table-ordering.txt」を参照し、
	// 順次テーブルを作成することでテスト用のテーブル群を作成する
	// このとき、valueのパスは「src/test/recources」配下を起点とし、CSVファイルのファイル名は
	// テーブルの名前と対応させることとする
	//
	// また、@Transactionalアノテーションを付与することで、テストが終わるとトランザクションをロールバックすることで
	// 環境を汚すことなくテストができる
	@Test
	@DatabaseSetup(value = "/testData/")
	@Transactional
	public void findAllUserでユーザレコードが取得できる() throws Exception {
		List<User> userList = userDao.findAllUser();
		
		// Daoで正常にテーブルからレコードを取得できたか
		assertThat(userList.size(), is(2));
	}

}
