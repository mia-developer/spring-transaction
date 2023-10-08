package org.example.spring.transaction.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("org.example.spring.transaction")
@Import(JpaDataSourceConfig.class)
public class ApplicationConfig {

}
