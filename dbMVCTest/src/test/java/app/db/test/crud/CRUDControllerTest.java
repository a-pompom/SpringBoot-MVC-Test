package app.db.test.crud;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import app.db.controller.DBCrudController;
import app.db.main.DbMvcTestApplication;
import app.db.test.CsvDataSetLoader;

/**
 * コントローラのテストクラス
 * 画面へ渡されるモデル・POSTリクエストでDBが更新されるかを検証する
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
@AutoConfigureMockMvc
@SpringBootTest(classes = {DBCrudController.class, DbMvcTestApplication.class})
@Transactional
public class CRUDControllerTest {
	
	//mockMvc TomcatサーバへデプロイすることなくHttpリクエスト・レスポンスを扱うためのMockオブジェクト
	@Autowired
	private MockMvc mockMvc;
	
	// DBUnitでテストデータを設定し、Daoから取得した結果へCSV内のレコードが存在するか検証
	// @Transactionalにより、実行後はロールバックされ、DBは汚れることなく保たれる
	@Test
	@DatabaseSetup(value = "/CRUD/setUp/")
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
	
	@Test
	@DatabaseSetup(value = "/CRUD/setUp/")
	@ExpectedDatabase(value = "/CRUD/update/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void フォームの入力値でDBが更新される() throws Exception {
		
		MvcResult result = mockMvc.perform(post("/crud/save/0/1")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("userList[0].userName", "test1mod")).andDo(print()).andReturn();
	}
	
	@Test
	@DatabaseSetup(value = "/CRUD/setUp/")
	@ExpectedDatabase(value = "/CRUD/delete/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void フォームからDBのレコードが削除できる() throws Exception {
		
		mockMvc.perform(post("/crud/delete/1")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED));
				
	}

}
