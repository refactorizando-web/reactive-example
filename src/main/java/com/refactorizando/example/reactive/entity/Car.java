package com.refactorizando.example.reactive.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {

  @Id
  private Long id;

  private String model;

  private String brand;

  private String color;

}
