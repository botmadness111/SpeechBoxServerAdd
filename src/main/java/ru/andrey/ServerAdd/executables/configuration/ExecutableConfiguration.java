package ru.andrey.ServerAdd.executables.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.andrey.ServerAdd.executables.utils.OriginalAndTranslation;

@Configuration
public class ExecutableConfiguration {

    @Bean
    public OriginalAndTranslation originalAndTranslation() {
        return new OriginalAndTranslation();
    }
}
