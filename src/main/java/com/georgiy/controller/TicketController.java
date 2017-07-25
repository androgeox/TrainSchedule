package com.georgiy.controller;

import com.georgiy.model.Ticket;
import com.georgiy.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

  @Autowired
  private TicketService ticketService;

  @RequestMapping(value = "/date", method = RequestMethod.GET)
  public ResponseEntity getTicketByDate(Date date){
    List<Ticket> ticketByDate = ticketService.getTicketByDate(date);
    return ResponseEntity.ok(ticketByDate);
  }
}
