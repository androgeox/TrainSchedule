package com.georgiy.service;

import com.georgiy.model.Ticket;
import com.georgiy.repository.CustomerRepository;
import com.georgiy.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private TicketRepository ticketRepository;

  public void buyTicket(Integer id) {
    Ticket ticket = getTicketById(id);
    ticket.setState(Ticket.TicketState.getSold());
  }

  public Ticket getTicketById(Integer id) {
    return ticketRepository.findOne(id);
  }
}
