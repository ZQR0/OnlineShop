package ru.os.OnlineShop.config;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
@Slf4j
public class ModelMapperConfig {

    //ModelMapper bean to create DTOs
    @Bean(name = "modelmapper_bean")
    public ModelMapper modelMapper() {
        log.info("ModelMapper bean created");
        return new ModelMapper();
    }
}
