package com.georgiy.repository;

import com.georgiy.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Integer>{
  List<Ticket> getTicketByDate(Date date);

  @Query("select t from Ticket t where state = 'в наличие'")
  List<Ticket> getAllForSell();
}
