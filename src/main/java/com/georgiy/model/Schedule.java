package com.georgiy.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
public class Schedule {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  Date date;

  @OneToMany(mappedBy = "schedule")
  private Set<Train> trainSet;

}
