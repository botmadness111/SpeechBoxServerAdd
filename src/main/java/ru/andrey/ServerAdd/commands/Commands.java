package ru.andrey.ServerAdd.commands;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Commands {
    @Getter
    private final List<Command> commands;

    @Autowired
    public Commands(List<Command> commands) {
        this.commands = commands;
    }


//    @PostConstruct
//    private void setCommands() {
//        Map<String, Command> commandBeans = applicationContext.getBeansOfType(Command.class);
//
//        for (Map.Entry<String, Command> entry : commandBeans.entrySet()) {
//            String beanName = entry.getKey();
//            Command command = entry.getValue();
//            System.out.println(command.description());
//
//            this.commands.add(command);
//        }
//    }

}
