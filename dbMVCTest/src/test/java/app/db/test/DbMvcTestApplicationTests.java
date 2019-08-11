package app.db.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import app.db.controller.DBController;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DBController.class)
@AutoConfigureMockMvc
public class DbMvcTestApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() throws Exception {
		
		this.mockMvc.perform(get("/db/init")).andDo(print())
			.andExpect(status().isOk());
	}

}
