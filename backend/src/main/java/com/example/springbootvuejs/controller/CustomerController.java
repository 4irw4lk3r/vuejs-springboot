package com.example.springbootvuejs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootvuejs.model.Customer;
import com.example.springbootvuejs.repository.CustomerRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerRepository repository;
	
	@GetMapping("/all")
	public List<Customer> getAllCustomers(){
		List<Customer> customers = new ArrayList<>();
		repository.findAll().forEach(customers::add);
		
		return customers;
	}
	
	@GetMapping("/age/{age}")
	public List<Customer> findByAge(@PathVariable int age) {
 
		List<Customer> customers = repository.findByAge(age);
		return customers;
	}
	
	@GetMapping("/{id}")
	public  Optional<Customer> findById(@PathVariable long id) {
 		return repository.findById(id);
	}
	
	@PostMapping("/add")
	public Customer postCustomer(@RequestBody Customer customer) {
 
		Customer _customer = repository.save(new Customer(customer.getFirstName(), customer.getLastName(), customer.getAge()));
		return _customer;
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") long id) {
		System.out.println("Delete Customer with ID = " + id + "...");
 
		repository.deleteById(id);
 
		return new ResponseEntity<>("Customer has been deleted!", HttpStatus.OK);
	}
	
	@PutMapping("/customer/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
		System.out.println("Update Customer with ID = " + id + "...");
 
		Optional<Customer> customerData = repository.findById(id);
 
		if (customerData.isPresent()) {
			Customer _customer = customerData.get();
			_customer.setFirstName(customer.getFirstName());
			_customer.setLastName(customer.getLastName());
			_customer.setAge(customer.getAge());
			_customer.setActive(customer.isActive());
			return new ResponseEntity<>(repository.save(_customer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
