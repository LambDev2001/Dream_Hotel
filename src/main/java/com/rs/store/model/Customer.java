package com.rs.store.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Customer {
//    @Column(name = "id")  khi ten bien khac voi cot trong database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String role;
}
