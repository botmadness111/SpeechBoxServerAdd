package ru.andrey.ServerAdd.executables;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutableConfiguration {

    @Bean
    public OriginalAndTranslation originalAndTranslation() {
        return new OriginalAndTranslation();
    }
}
