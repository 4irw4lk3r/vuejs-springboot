package com.example.springbootvuejs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.springbootvuejs.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
	List<Customer> findByAge(int age);
}
