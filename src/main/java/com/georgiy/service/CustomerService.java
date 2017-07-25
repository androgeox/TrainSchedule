package com.georgiy.service;

import com.georgiy.model.Customer;
import com.georgiy.model.Ticket;
import com.georgiy.repository.CustomerRepository;
import com.georgiy.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private TicketRepository ticketRepository;

  public void buyTicket(){
    List<Ticket> all = ticketRepository.getAllForSell();
    for(Ticket item: all){
      if(Ticket.TicketState.getSold() != null){
          item.setState(Ticket.TicketState.getSold());
      }
    }
  }
}
