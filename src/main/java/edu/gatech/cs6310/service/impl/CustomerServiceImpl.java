package edu.gatech.cs6310.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.gatech.cs6310.domain.Customer;
import edu.gatech.cs6310.repository.CustomerRepository;
import edu.gatech.cs6310.service.CustomerService;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    //@Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(String firstName, String lastName, String phone, String account, int rating, int credit) {
        Customer existingCustomer = customerRepository.findByAccount(account);
        if (existingCustomer != null) {
            throw new IllegalArgumentException("ERROR:customer_identifier_already_exists");
        }
        Customer customer = new Customer(firstName, lastName, phone, account, rating, credit);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findByAccount(String account) {
        return customerRepository.findByAccount(account);
    }

}
