package com.refactorizando.example.reactive.configuration;

import com.refactorizando.example.reactive.repository.CarRepository;
import com.refactorizando.example.reactive.routes.CarHandler;
import com.refactorizando.example.reactive.service.CarService;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class ServiceConfiguration {

  @Bean
  public ConnectionFactoryInitializer initialize(ConnectionFactory factory) {
    var initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(factory);
    var populator = new CompositeDatabasePopulator();
    populator.addPopulators(
        new ResourceDatabasePopulator(new ClassPathResource("/schema.sql")),
        new ResourceDatabasePopulator(new ClassPathResource("/data.sql"))
    );
    initializer.setDatabasePopulator(populator);
    return initializer;
  }

  @Bean
  public CarService service(CarRepository repository) {
    return new CarService(repository);
  }

  @Bean
  public CarHandler handler(CarService service) {
    return new CarHandler(service);
  }

}
