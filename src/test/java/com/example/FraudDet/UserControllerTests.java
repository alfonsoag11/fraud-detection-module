package com.example.FraudDet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.FraudDet.Entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getAllUsers() throws Exception {
		this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$._embedded.userList[:1].firstName").value("Albert"));
	}

	@Test
	void createUser() throws Exception {
		this.mockMvc.perform(
	      post("/users")
	      .content(asJsonString(new User("Shaun", "Lama", "+89542124578", "slnew@mailo.com")))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated()) // 201
      .andExpect(jsonPath("$.id").exists());
	}

	@Test
	void modifyUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders
	      .put("/users/{id}", 1)
	      .content(asJsonString(new User("Albert", "Green-Garcia", "+89542124578", "newemail@mailo.com")))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated()) // 201 because the entity is replaced
      .andExpect(jsonPath("$.firstName").value("Albert"))
      .andExpect(jsonPath("$.lastName").value("Green-Garcia"))
      .andExpect(jsonPath("$.email").value("newemail@mailo.com"));
	}

	@Test
	void deleteUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders
	      .delete("/users/{id}", 2))
		  .andExpect(status().isNoContent()); // 204
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
