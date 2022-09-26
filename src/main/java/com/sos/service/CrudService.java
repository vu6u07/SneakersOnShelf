package com.sos.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, K> {

	List<T> findAll();

	Optional<T> findById(K id);

	T save(T entity);

	void deleteById(K id);
	
}
