package ru.wm.WorkManager.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {

    //ModelMapper bean to create DTOs
    @Bean(name = "modelmapper_bean")
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
