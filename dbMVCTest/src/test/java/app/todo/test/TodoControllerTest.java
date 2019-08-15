package app.todo.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import app.db.controller.TodoController;

/**
 * TODOリストのコントローラのテストクラス
 * @author aoi
 *
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = TodoController.class)
public class TodoControllerTest {
	
	//mockMvc TomcatサーバへデプロイすることなくHttpリクエスト・レスポンスを扱うためのMockオブジェクト
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void init処理でviewとしてtodoが渡される() throws Exception {
		this.mockMvc.perform(get("/todo/init")).andDo(print())
			.andExpect(status().isOk())
			.andExpect(view().name("todo"));
	}
	
	@Test
	void init処理で既存のタスクがモデルへ渡される() {
		
		// mockMvcで「/todo/init」へgetリクエストを送信
		
		// モデルへDBのレコードがリストとして渡される
	}
	
	@Test
	void save処理で新規タスクがDBへ登録される() {
		
		// mockMvcで「todo/save」へpostリクエストを送信
		
		// 画面の入力値をもとにDBへ新規レコードが登録される
		
	}
	
	@Test
	void update処理で既存タスクが更新される() {
		
		// mockMvcで「todo/update」へpostリクエストを送信
		
		// 画面で選択されたタスクと対応したDBの既存レコードが更新される
		
	}
	
	@Test
	void delete処理で既存タスクが消去される() {
		
		// mockMvcで「todo/delete」へpostリクエストを送信
		
		// 画面で選択されたタスクと対応したDBの既存レコードが削除される
	}

}
