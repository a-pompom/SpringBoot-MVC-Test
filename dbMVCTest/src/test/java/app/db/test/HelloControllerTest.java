package app.db.test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import app.db.controller.HelloController;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = HelloController.class)
public class HelloControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	void init処理が走って2000が返る() throws Exception {
		
		this.mockMvc.perform(get("/hello/init")).andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	void init処理でモデルのメッセージにhelloが渡される() throws Exception {
		this.mockMvc.perform(get("/hello/init"))
			.andExpect(model().attribute("message", "hello!"));
	}
	
	@Test
	void init処理でモデルのフォームへユーザリストが格納されるか() throws Exception {
		
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
