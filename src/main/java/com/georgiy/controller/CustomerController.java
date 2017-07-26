package com.georgiy.controller;

import com.georgiy.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @RequestMapping(value = "/buy/{id}")
  public ResponseEntity buyTicket(@PathVariable("id") Integer id) {
    customerService.buyTicket(id);
    return ResponseEntity.ok("билет куплен");
  }
}
