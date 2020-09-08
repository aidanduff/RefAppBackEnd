package com.aidanduff.referenceapp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aidanduff.referenceapp.entity.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, String> {

	Item findById(long id);
	
	void deleteById(long id);

}
