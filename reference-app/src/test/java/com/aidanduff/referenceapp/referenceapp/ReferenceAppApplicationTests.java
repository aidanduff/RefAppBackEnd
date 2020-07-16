package com.aidanduff.referenceapp.referenceapp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aidanduff.referenceapp.controller.ItemController;
import com.aidanduff.referenceapp.service.ItemService;

@SpringBootTest
class ReferenceAppApplicationTests {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemController itemController;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void contollerLoads() throws Exception {
		assertThat(itemController).isNotNull();
	}
	
	@Test
	public void serviceLoads() throws Exception {
		assertThat(itemService).isNotNull();
	}	
}
