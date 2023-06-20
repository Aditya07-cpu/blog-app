package com.blog.application.project.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean("modelMapper")
    public ModelMapper modelMapperConfig() {
        return new ModelMapper();
    }
}
