package com.aidanduff.referenceapp.referenceapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.aidanduff.referenceapp.controller.ItemController;
import com.aidanduff.referenceapp.entity.Item;
import com.aidanduff.referenceapp.service.ItemService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReferenceAppApplicationTests {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
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
	
	@Test
	public void itemShouldBeCreated() throws Exception {
		Item item = new Item(1, "Bird", "Two Legged Animal");
		HttpEntity<Item> request = new HttpEntity<>(item);
		ResponseEntity<Item> result = this.restTemplate.postForEntity("http://localhost:" + port + "/items", request, Item.class);
		assertEquals(201, result.getStatusCodeValue());
	}
	
	@Test
	public void AllItemsShouldBeRetrieved() throws Exception {
		Item item = new Item(1, "Bird", "Two Legged Animal");
		HttpEntity<Item> request = new HttpEntity<>(item);
		this.restTemplate.postForEntity("http://localhost:" + port + "/items", request, Item.class);
		
		Item item2 = new Item(2, "Ball", "Spherical sporting equipment");
		HttpEntity<Item> request2 = new HttpEntity<>(item2);
		this.restTemplate.postForEntity("http://localhost:" + port + "/items", request2, Item.class);
		
		ResponseEntity<List> result = this.restTemplate.getForEntity("http://localhost:" + port + "/items", List.class);
		assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void itemShouldBeRetrieved() throws Exception {
		Item item = new Item(1, "Bird", "Two Legged Animal");
		HttpEntity<Item> request = new HttpEntity<>(item);
		this.restTemplate.postForEntity("http://localhost:" + port + "/items", request, Item.class);
		ResponseEntity<Item> result = this.restTemplate.getForEntity("http://localhost:" + port + "/item/1", Item.class);
		assertEquals(302, result.getStatusCodeValue());
	}
	
	@Test
	public void itemShouldBeUpdated() throws Exception {
		System.err.println("in put test");
		Item item = new Item(1, "Bird", "Two Legged Animal");
		HttpEntity<Item> request = new HttpEntity<>(item);
		this.restTemplate.postForEntity("http://localhost:" + port + "/items", request, Item.class);
		
		Item updatedItem = new Item(1, "Cow", "Four Legged Animal");
		HttpEntity<Item> newRequest = new HttpEntity<>(updatedItem);
		ResponseEntity<Item> result = restTemplate.exchange("http://localhost:" + port + "/item/1", HttpMethod.PUT, newRequest, Item.class);
		assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void itemShouldBeDeleted() throws Exception {
		Item item = new Item(1, "Bird", "Two Legged Animal");
		HttpEntity<Item> request = new HttpEntity<>(item);
		this.restTemplate.postForEntity("http://localhost:" + port + "/items", request, Item.class);
		ResponseEntity<Item> result = restTemplate.exchange("http://localhost:" + port + "/item/1", HttpMethod.DELETE, request, Item.class);
		assertEquals(204, result.getStatusCodeValue());
	}
	
	@Test
	public void itemShouldNotBeFound() throws Exception {
		Item item = new Item(1, "Bird", "Two Legged Animal");
		HttpEntity<Item> request = new HttpEntity<>(item);
		this.restTemplate.postForEntity("http://localhost:" + port + "/items", request, Item.class);
		
		ResponseEntity<Item> result = this.restTemplate.getForEntity("http://localhost:" + port + "/item/2", Item.class);
		assertEquals(404, result.getStatusCodeValue());
	}
	
	@Test
	public void itemtoDeleteShouldNotBeFound() throws Exception {
		Item item = new Item(1, "Bird", "Two Legged Animal");
		HttpEntity<Item> request = new HttpEntity<>(item);
		this.restTemplate.postForEntity("http://localhost:" + port + "/items", request, Item.class);
		ResponseEntity<Item> result = restTemplate.exchange("http://localhost:" + port + "/item/2", HttpMethod.DELETE, request, Item.class);
		assertEquals(404, result.getStatusCodeValue());
	}

}
