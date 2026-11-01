package com.atenea.unimarket;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ConfiguracionGeneral {

    @Bean
    Clock reloj() {
        return Clock.systemUTC();
    }
}
