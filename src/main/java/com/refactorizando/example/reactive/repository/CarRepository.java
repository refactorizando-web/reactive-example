package com.refactorizando.example.reactive.repository;

import com.refactorizando.example.reactive.entity.Car;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface CarRepository extends ReactiveSortingRepository<Car, Long> {

}
