package yte.intern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import yte.intern.service.impl.DatabasePopulationService;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {

    private final DatabasePopulationService databasePopulationService;

    public Application(final DatabasePopulationService databasePopulationService) {
        this.databasePopulationService = databasePopulationService;
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void runTests() {
        databasePopulationService.populateDatabase();
    }

}