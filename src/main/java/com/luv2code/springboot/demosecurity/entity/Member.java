package com.luv2code.springboot.demosecurity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Member {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private String user_id;

    @Column(name="pw")
    private String password;

    @Column(name="active")
    private int active=1;


}
