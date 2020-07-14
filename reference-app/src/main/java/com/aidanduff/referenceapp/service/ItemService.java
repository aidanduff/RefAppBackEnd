package com.aidanduff.referenceapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.aidanduff.referenceapp.dao.ItemRepository;
import com.aidanduff.referenceapp.entity.Item;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> getAllItems(){
		List<Item> items = new ArrayList<>();
		
		itemRepository.findAll()
			.forEach(items::add);
		
		return items;
	}

	public Item addItem(Item item) {
		return itemRepository.save(item);	
	}
	
	public void updateItem(long id, Item item) {
		itemRepository.delete(getItem(id));
		itemRepository.save(item);
	}

	public Item getItem(long id) {
		return itemRepository.findById(id);
	}

	public Item deleteItem(long id) {
		Item item = itemRepository.findById(id);
		if(item == null) {
			return null;
		}
		
		itemRepository.deleteById(id);
		return item;
	}
}
