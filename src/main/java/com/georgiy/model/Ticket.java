package com.georgiy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ticket {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  Integer price;
  Date buyDate;
  Integer sitNumber;
  String state;

  public enum TicketState {
    SOLD, FORSELL;

    public static String getSold() {
      return SOLD.toString();
    }

    public static String getForSell() {
      return FORSELL.toString();
    }
  }

  Date beginDate;
  Date endDate;

  @ManyToOne
  @JoinColumn(name = "customer")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "train")
  private Train train;
}
