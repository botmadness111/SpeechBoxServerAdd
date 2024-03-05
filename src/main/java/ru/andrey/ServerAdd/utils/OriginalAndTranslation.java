package ru.andrey.ServerAdd.utils;

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
    @Autowired
    @Lazy
    private List<Executable> executables;

    @Getter
    private final String keyOriginal = "original";
    @Getter
    private final String keyTranslation = "translation";

    private final HashMap<String, String> map;

    public OriginalAndTranslation() {
        map = new HashMap<>(Map.of("original", "", "translation", ""));
    }


    public Map<String, String> getOriginalAndTranslate(String text) {
        String original = text.split(":")[0].trim();
        for (Executable executable : executables) {
            String[] arrString = original.split(executable.command() + " ");
            if (arrString.length == 2) original = arrString[1].trim();
        }

        String translation = text.split(":")[1].trim();

        map.put(keyOriginal, original);
        map.put(keyTranslation, translation);

        return map;
    }
}
