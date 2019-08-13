package app.db.test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class DBCrudTest {
	
	//mockMvc TomcatサーバへデプロイすることなくHttpリクエスト・レスポンスを扱うためのMockオブジェクト
	@Autowired
	private MockMvc mockMvc;
	
	
	@DatabaseSetup(value = "/testData/")
	@Transactional
	void ステータス200が返る() throws Exception {
		
		this.mockMvc.perform(get("/crud/init"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("dbForm", hasProperty(
					"userList", hasItem(
						hasProperty(
								"userName", is("test1")
						)
					)
			)));
	}
	
	@Test
	@DatabaseSetup(value = "/testData/")
	@ExpectedDatabase(value = "/expectedData/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void フォームの入力値でDBが更新される() throws Exception {
		
		MvcResult result = this.mockMvc.perform(post("/crud/save/0/1")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("userList[0].userName", "test1mod")).andDo(print()).andReturn();
		
	}
	
	@DatabaseSetup(value = "/testData/")
	@ExpectedDatabase(value = "/expectedData/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void 削除ボタンクリックでユーザが削除される() throws Exception {
		
		MvcResult result = this.mockMvc.perform(post("/crud/delete/1")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				).andDo(print()).andReturn();
		
		
	}
	
	

}
