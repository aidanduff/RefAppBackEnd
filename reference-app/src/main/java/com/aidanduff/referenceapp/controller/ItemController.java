package com.aidanduff.referenceapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aidanduff.referenceapp.entity.Item;
import com.aidanduff.referenceapp.service.ItemService;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping("/items")
	public ResponseEntity<List<Item>> getAllItems() {
		List<Item> items = itemService.getAllItems();
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
	
	@GetMapping("/item/{id}")
	public ResponseEntity<?> getItem(@PathVariable long id) {
		Item item = itemService.getItem(id);
		return item != null? new ResponseEntity<>(item, HttpStatus.FOUND): ResponseEntity.notFound().build();
	}
	
	@PostMapping("/items")
	public ResponseEntity<Item> addItem(@RequestBody Item item){
		itemService.addItem(item);
		return new ResponseEntity<>(item, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/item/{id}")
	public ResponseEntity<Item> updateItem(@RequestBody Item item, @PathVariable long id){
		itemService.updateItem(id, item);
		return new ResponseEntity<>(item, HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("/item/{id}")
	public ResponseEntity<Item> deleteItem(@PathVariable long id){
		Item item = itemService.deleteItem(id);
		return item != null? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}
}
