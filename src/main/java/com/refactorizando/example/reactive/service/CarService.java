package com.refactorizando.example.reactive.service;

import com.refactorizando.example.reactive.entity.Car;
import com.refactorizando.example.reactive.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class CarService {

  private final CarRepository carRepository;

  public Mono<Car> findById(Long id) {
    return carRepository
        .findById(id)
        .doOnNext(p -> log.info("Car with id " + p.getId()));
  }

  public Mono<Void> deleteById(Long id) {
    return carRepository.deleteById(id).doOnNext(c -> log.info("Car with id {} deleted", id));
  }

  public Mono<Car> save(Car car) {

    return carRepository.save(car);
  }

  public Flux<Car> findAll(Sort sort) {
    return carRepository.findAll(sort);
  }
}
