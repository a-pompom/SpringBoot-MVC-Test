package app.db.test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import app.db.main.DbMvcTestApplication;

/**
 * DBUnitで作成したテストデータがモデルに正常に渡されるか検証するためのテストクラス
 * @author aoi
 *
 */
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
	  DependencyInjectionTestExecutionListener.class,
	  TransactionalTestExecutionListener.class,
	  DbUnitTestExecutionListener.class
	})
@AutoConfigureMockMvc
@SpringBootTest(classes = {DbMvcTestApplication.class})
public class DBModelTest {
	
	//mockMvc TomcatサーバへデプロイすることなくHttpリクエスト・レスポンスを扱うためのMockオブジェクト
	@Autowired
	private MockMvc mockMvc;
	
	// DBUnitでテストデータを設定し、Daoから取得した結果へCSV内のレコードが存在するか検証
	// @Transactionalにより、実行後はロールバックされ、DBは汚れることなく保たれる
	@Test
	@DatabaseSetup(value = "/testData/")
	@Transactional
	void DBからの取得結果が全てモデルへ格納される() throws Exception {
		
		this.mockMvc.perform(get("/helloDB/init")).andDo(print())
			.andExpect(model().attribute("dbForm", hasProperty(
					"userList", hasItem(
						hasProperty(
								"userName", is("test1")
						)
					)
			)));	
	}
}
