package ru.andrey.ServerAdd.executables;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.andrey.ServerAdd.utils.OriginalAndTranslation;

@Configuration
public class ExecutableConfiguration {

    @Bean
    public OriginalAndTranslation originalAndTranslation() {
        return new OriginalAndTranslation();
    }
}
