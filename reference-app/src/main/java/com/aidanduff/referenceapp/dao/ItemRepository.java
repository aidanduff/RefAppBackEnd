package com.aidanduff.referenceapp.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.aidanduff.referenceapp.entity.Item;

public interface ItemRepository extends CrudRepository<Item, String> {

	Item findById(long id);
	
	void deleteById(long id);

}
