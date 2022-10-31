package com.sos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<T, K> {

	List<T> findAll();

	Page<T> findAll(Pageable pageable);

	Optional<T> findById(K id);


	T save(T entity);

	void deleteById(K id);

}
