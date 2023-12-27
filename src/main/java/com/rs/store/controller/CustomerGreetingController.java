package com.rs.store.controller;

import com.rs.store.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController // Lay response
@RequestMapping("/v1") // Version
public class CustomerGreetingController {

    private static final String greetingTemplate = "Hello, %s %s";
    private final AtomicLong counter = new AtomicLong(); // Tang dan so
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "gender", defaultValue = "0") boolean gender,
                             @RequestParam(value = "customerName", defaultValue = "Customer") String customerName
                             ){
        return Greeting.builder()
                .id(counter.incrementAndGet())
                .content(String.format(greetingTemplate, gender ? "Mr." : "Ms.", customerName))
                .build();
    }
}
