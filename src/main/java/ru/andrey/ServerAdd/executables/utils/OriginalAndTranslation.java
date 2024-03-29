package ru.andrey.ServerAdd.executables.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.andrey.ServerAdd.executables.Executable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OriginalAndTranslation {

    @Getter
    private final String keyOriginal = "original";
    @Getter
    private final String keyTranslation = "translation";

    private final HashMap<String, String> map;

    public OriginalAndTranslation() {
        map = new HashMap<>(Map.of(keyOriginal, "", keyTranslation, ""));
    }


    public Map<String, String> getOriginalAndTranslate(String text) {
        String original = text.split(":")[0].split("/add")[1].trim();

        String translation = text.split(":")[1].trim();

        map.put(keyOriginal, original);
        map.put(keyTranslation, translation);

        return map;
    }
}
