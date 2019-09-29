package app.todo.test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;


import app.db.main.DbMvcTestApplication;
import app.db.test.CsvDataSetLoader;

/**
 * TODOリストのコントローラのテストクラス
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
@SpringBootTest(classes = {DbMvcTestApplication.class})
@Transactional
public class TodoControllerTest {
	
	//mockMvc TomcatサーバへデプロイすることなくHttpリクエスト・レスポンスを扱うためのMockオブジェクト
	@Autowired
	private MockMvc mockMvc;
	
	/**
	 * viewが正しく返されるか検証
	 * @throws Exception
	 */
	@Test
	void init処理でviewとしてtodoが渡される() throws Exception {
		this.mockMvc.perform(get("/todo/init"))
			.andExpect(status().isOk())
			.andExpect(view().name("todo"));
	}
	
	/**
	 * モデルへDBから取得したレコードが設定されたか検証する
	 * 今回は複雑な処理でもないので、DBの中の1レコードがモデルに渡されていれば正常に動作しているとみなした
	 * 
	 * @throws Exception
	 */
	@Test
	@DatabaseSetup(value = "/TODO/setUp/")
	void init処理で既存のタスクがモデルへ渡される() throws Exception {
		
		// mockMvcで「/todo/init」へgetリクエストを送信
		this.mockMvc.perform(get("/todo/init"))
		// モデルへDBのレコードがリストとして渡される
			.andExpect(model().attribute("todoForm", hasProperty(
					"todoList", hasItem(
							hasProperty(
									"task", is("task1")
							)
					)
			)));
	}
	
	/**
	 * 画面の入力から新規レコードがDBへ登録されるか検証
	 * @throws Exception
	 */
	@Test
	@DatabaseSetup(value = "/TODO/setUp/")
	@ExpectedDatabase(value = "/TODO/create/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void save処理で新規タスクがDBへ登録される() throws Exception {
		
		this.mockMvc.perform(post("/todo/save")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("newTask", "newTask"));
		
	}
	@Test
	void 空文字で登録ボタンをクリックするとエラーとなる() throws Exception {
		
		this.mockMvc.perform(post("/todo/save")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.param("newTask", ""))
			.andExpect(model().hasErrors()) // 新規タスク部分にエラーが存在するか
			.andExpect(model().errorCount(1))
			.andExpect(model().attributeHasFieldErrors("todoForm", "newTask"))
			.andExpect(view().name("todo"));
		
	}
	
	/**
	 * 画面の入力で既存レコードが更新されるか検証
	 * 今回は画面情報を利用しないので、対象の自動採番されるIDを取得することができない。
	 * これを解決するため、ヘルパーを経由して対象のIDを取得することで遷移先のURIを明示
	 * @throws Exception
	 */
	@Test
	@DatabaseSetup(value = "/TODO/setUp/")
	@ExpectedDatabase(value = "/TODO/update/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void update処理で既存タスクが更新される() throws Exception{
		
		// mockMvcで「todo/update」へpostリクエストを送信
		long updateTargetId = 3L;
		int updateTargetIndex = 2;
		
		this.mockMvc.perform(post("/todo/update/" + updateTargetIndex + "/" + updateTargetId)
				.param("todoList[" + updateTargetIndex + "].task", "task3mod")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				);
		
	}
	/**
	 * 画面で選択したタスクが削除されるかどうか検証する
	 * 更新処理と同様、URIで指定する対象はヘルパーから取得
	 * @throws Exception
	 */
	@Test
	@DatabaseSetup(value = "/TODO/setUp/")
	@ExpectedDatabase(value = "/TODO/delete/", assertionMode=DatabaseAssertionMode.NON_STRICT)
	void delete処理で既存タスクが消去される() throws Exception {
		long deleteTargetId = 3L;
		
		this.mockMvc.perform(post("/todo/delete/" + deleteTargetId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				);
		
	}
}
