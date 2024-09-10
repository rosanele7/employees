package com.employees.service;

import com.employees.model.Employee;
import com.employees.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static Logger LOG = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository) {
        return args -> {
            LOG.info("Preloading " + repository.save(new Employee("Rosario", "Gordillo", "rosario.gordillo@email.com")));
            LOG.info("Preloading " + repository.save(new Employee("Francisco", "Lugo", "francisco.lugo@email.com")));
            LOG.info("Preloading " + repository.save(new Employee("Mariana", "Gordillo", "mariana.gordillo@email.com")));
            LOG.info("Preloading " + repository.save(new Employee("Miguel", "Silva", "miguel.silva@email.com")));
            LOG.info("Preloading " + repository.save(new Employee("Shanad", "Lugo", "shanad.lugo@email.com")));
            LOG.info("Preloading " + repository.save(new Employee("Joaquin", "Lopez", "joaquin.lopez@email.com")));
            LOG.info("Preloading " + repository.save(new Employee("Laura", "Lugo", "laura.lugo@email.com")));
            LOG.info("Preloading " + repository.save(new Employee("Carlos", "Fiestas", "carlos.fiestas@email.com")));
            LOG.info("Preloading " + repository.save(new Employee("Citally", "Guerrero", "citlally.guerrero@email.com")));
            LOG.info("Preloading " + repository.save(new Employee("Oswaldo", "Perez", "oswaldo.perez@email.com")));
        };
    }
}
