package com.refactorizando.example.reactive.routes;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.refactorizando.example.reactive.entity.Car;
import com.refactorizando.example.reactive.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class CarHandler {

  private final CarService service;

  public Mono<ServerResponse> getAll(ServerRequest req) {
    var all = service.findAll(Sort.by("model", "brand"));
    return ok().body(fromPublisher(all, Car.class));
  }

  public Mono<ServerResponse> getOne(ServerRequest req) {
    var mono = service
        .findById(Long.valueOf(req.pathVariable("id")))
        .switchIfEmpty(Mono.error(() -> new ResponseStatusException(NOT_FOUND)));
    return ok().body(fromPublisher(mono, Car.class));
  }

  public Mono<ServerResponse> save(ServerRequest req) {

    final Mono<Car> car = req.bodyToMono(Car.class);
    return ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(fromPublisher(car.flatMap(service::save), Car.class));
  }

  public Mono deleteById(ServerRequest req) {

    var id = Long.valueOf(req.pathVariable("id"));
    return ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(service.deleteById(id), Void.class);
  }
}
