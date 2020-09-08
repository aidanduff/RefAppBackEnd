package com.aidanduff.referenceapp.referenceapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.aidanduff.referenceapp.controller.ItemController;
import com.aidanduff.referenceapp.entity.Item;
import com.aidanduff.referenceapp.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest
@RunWith(SpringRunner.class)
public class ItemControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ItemController itemController;
	
	@MockBean
	private ItemService itemService;
	
	@Test
	public void listShouldbeReturnedFromGetAll() throws Exception {
		List<Item> itemList = new ArrayList<>();
		itemList.add(new Item(1, "Bird", "Winged Animal"));
		when(itemService.getAllItems())
				.thenReturn(itemList);
		
		this.mockMvc.perform(get("/items"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));	
		
		assertEquals(ArrayList.class, itemController.getAllItems().getBody().getClass());
	}
	
	@Test
	public void listReturnedFromGetAllShouldBeSpecificSize() throws Exception {
		List<Item> itemList = new ArrayList<>();
		itemList.add(new Item(1, "Bird", "Winged Animal"));
		when(itemService.getAllItems())
				.thenReturn(itemList);
		
		this.mockMvc.perform(get("/items"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));	
		
		assertEquals(1, itemController.getAllItems().getBody().size());
	}
	
	@Test
	public void getAllResultShouldHaveNoBody() throws Exception {
		List<Item> itemList = new ArrayList<>();
		when(itemService.getAllItems())
				.thenReturn(itemList);
		
		this.mockMvc.perform(get("/items"))
			.andDo(print())
			.andExpect(status().isNoContent());
		
		assertFalse(itemController.getAllItems().hasBody());
	}
	
	@Test
	public void postNewItemShouldSucceed() throws Exception {
		Item item = new Item(1, "Bird", "Winged Animal");
		String json = new ObjectMapper().writeValueAsString(item);
		
		when(itemService.addItem(item))
			.thenReturn(item);
		
		this.mockMvc.perform(post("/items")
				.contentType(MediaType.APPLICATION_JSON)
	            .content(json)
	            .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("{'id': 1}"))
				.andExpect(content().json("{'name': 'Bird'}"))
				.andExpect(content().json("{'description': 'Winged Animal'}"));
	}
	
	@Test
	public void updateItemShouldSucceed() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		Item item = new Item(1, "Bird", "Winged Animal");
		item.setDescription("Beaked Animal");
		String updatedJson = objectMapper.writeValueAsString(item);
		
		when(itemService.addItem(item)).thenReturn(item);
		
		this.mockMvc.perform(put("/item/1")
				.contentType(MediaType.APPLICATION_JSON)
	            .content(updatedJson)
	            .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 1}"))
				.andExpect(content().json("{'name': 'Bird'}"))
				.andExpect(content().json("{'description': 'Beaked Animal'}"));
	}
	
	@Test
	public void getItemShouldSucceed() throws Exception {
		Item item = new Item(1, "Bird", "Winged Animal");

		when(itemService.addItem(item)).thenReturn(item);
		when(itemService.getItem(1)).thenReturn(item);
		
		this.mockMvc.perform(get("/item/1")
				.contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json("{'id': 1}"))
				.andExpect(content().json("{'name': 'Bird'}"))
				.andExpect(content().json("{'description': 'Winged Animal'}"));
	}
	
	@Test
	public void getNonExistantItemShouldBeNotFound() throws Exception {
		when(itemService.getItem(1)).thenReturn(null);
		
		this.mockMvc.perform(get("/item/1")
				.contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
                .andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteItemShouldSucceed() throws Exception {
		Item item = new Item(1, "Bird", "Winged Animal");

		when(itemService.deleteItem(1)).thenReturn(item);
				
		this.mockMvc.perform(delete("/item/1")
				.contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
                .andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteNonExistantItemShouldBeNotFound() throws Exception {

		when(itemService.deleteItem(2)).thenReturn(null);
		
		this.mockMvc.perform(delete("/item/2")
				.contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
                .andExpect(status().isNotFound());
	}
}
