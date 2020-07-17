package com.aidanduff.referenceapp.referenceapp;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.assertj.core.util.Lists;
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
		
		this.mockMvc.perform(get("/items")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));	
		
		assertEquals(ArrayList.class, itemController.getAllItems().getBody().getClass());
	}
	
	@Test
	public void listReturnedFromGetAllShouldBeSpecificSize() throws Exception {
		List<Item> itemList = new ArrayList<>();
		itemList.add(new Item(1, "Bird", "Winged Animal"));
		when(itemService.getAllItems())
				.thenReturn(itemList);
		
		this.mockMvc.perform(get("/items")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));	
		
		assertEquals(1, itemController.getAllItems().getBody().size());
	}
	
	@Test
	public void getAllShouldHaveEmptyBody() throws Exception {
		List<Item> itemList = new ArrayList<>();
		when(itemService.getAllItems())
				.thenReturn(itemList);
		
		this.mockMvc.perform(get("/items")).andDo(print()).andExpect(status().isNoContent());
		
		assertFalse(itemController.getAllItems().hasBody());
	}
}
