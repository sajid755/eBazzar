package com.shoppingcart.eBazzar.security.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BazzarConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
