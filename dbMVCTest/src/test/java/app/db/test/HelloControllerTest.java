package app.db.test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import app.db.main.DbMvcTestApplication;

/**
 * @ExtendWith...JUnit5でテストクラスを実行させるために必要なアノテーション
 * @AutoConfigureMvc...テストクラスでMockMvcを利用するために必要なアノテーション
 * @SpringBootTest...application.propertiesなどのSpringBootアプリ用の設定、
 * 	    classesプロパティに設定したクラスをDIコンテナから取得し、利用するためのアノテーション
 * @author aoi
 *
 */
@AutoConfigureMockMvc
@SpringBootTest(classes = DbMvcTestApplication.class)
public class HelloControllerTest {
	
	//mockMvc TomcatサーバへデプロイすることなくHttpリクエスト・レスポンスを扱うためのMockオブジェクト
	@Autowired
	private MockMvc mockMvc;
	
	// getリクエストでviewを指定し、httpステータスでリクエストの成否を判定
	@Test
	void init処理が走って200が返る() throws Exception {
		// andDo(print())でリクエスト・レスポンスを表示
		this.mockMvc.perform(get("/hello/init")).andDo(print())
			.andExpect(status().isOk());
	}
	
	// リクエストスコープに渡されるモデルを検証する
	// 簡単なアプリを作っていて起こるバグの大半は、モデルへ適切な値が渡されなかったことによるものなので、
	// モデルの検証は重要になると思われる
	@Test
	void init処理でモデルのメッセージにhelloが渡される() throws Exception {
		this.mockMvc.perform(get("/hello/init"))
			.andExpect(model().attribute("message", "hello!"));
	}
	
	@Test
	void init処理でモデルへユーザEntityが格納される() throws Exception {
		this.mockMvc.perform(get("/hello/init"))
			.andExpect(model()
					.attribute("user", hasProperty(
										"userName", is("test0")
										)
							)
					);
	}
	
	// リクエストスコープへ渡されたモデルの中で、入れ子となっている、かつリスト構造を持つものについて
	// 検証を行う。 ネストされたものについては、「hasProperty」でプロパティの存在検証を行い、
	// 最下層まで到達することで値の検証が可能となる。
	// リスト要素については、hasItemで順番を問わずリストへアクセスし、指定されたプロパティが指定の値となる要素が存在するかを検証。
	// 存在する場合のみテストをグリーンとする
	@Test
	void init処理でモデルのフォームへユーザリストが格納される() throws Exception {
		
		this.mockMvc.perform(get("/hello/init"))
			.andExpect(model().attribute("dbForm", hasProperty(
					"userList", hasItem(
						hasProperty(
								"userName", is("test1")
						)
					)
			)));
	}

}
