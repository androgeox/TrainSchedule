package com.georgiy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
public class Train {
  @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  Integer trainNumber;
  String type;
  Date departDT;
  Date destDT;
  Integer sitsCount;
  Integer sitNumber;

  @OneToMany(mappedBy = "train", fetch = FetchType.EAGER)
  private Set<Ticket> tickets;

  @ManyToOne
  @JoinColumn(name = "schedule")
  private Schedule schedule;
}
