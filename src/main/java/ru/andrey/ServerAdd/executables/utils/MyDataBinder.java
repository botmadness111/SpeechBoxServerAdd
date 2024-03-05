package ru.andrey.ServerAdd.executables.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.util.Optional;

public class MyDataBinder extends DataBinder {
    public MyDataBinder(Object target, Validator validator) {
        super(target);
        this.addValidators(validator);
    }

    public Optional<String> findErrors() {
        this.validate();

        String errorText = null;
        if (this.getBindingResult().hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : this.getBindingResult().getAllErrors()) {
                sb.append(error.getDefaultMessage());
            }
            errorText = sb.toString();
        }
        return Optional.ofNullable(errorText);
    }
}
