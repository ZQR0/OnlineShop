package ru.os.OnlineShop.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class MainConfig {

    //ModelMapper bean to create DTOs
    @Bean(name = "modelmapper_bean")
    public ModelMapper modelMapper() {
        Logger.getLogger("ModelMapper config logger").info("ModelMapper bean created");
        return new ModelMapper();
    }
}
