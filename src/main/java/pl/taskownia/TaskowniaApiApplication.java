package pl.taskownia;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.taskownia.service.UserService;

@SpringBootApplication
public class TaskowniaApiApplication {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(TaskowniaApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
