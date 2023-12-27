package com.rs.store.controller;

import com.rs.store.model.Customer;
import com.rs.store.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Controller + request body + bean = controller layer
@RequestMapping("v1")
public class RegisterController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody Customer customer){
        ResponseEntity<String> response = null;
        try {
            Customer saveCustomer = customerService.createCustomer(customer);
            if(saveCustomer.getId() > 0){
                response = ResponseEntity.status(HttpStatus.CREATED).body("Customer is created successfully for customer: "+customer.getUsername());

            }

        } catch (Exception exception){
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception occurred from server with exception: "+exception);
        }

        return response;
    }

}
