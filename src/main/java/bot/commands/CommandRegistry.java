package bot.commands;

import bot.commands.impl.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();
    private final Command unknownCommand;

    public CommandRegistry(List<Command> commands) {
        this.unknownCommand = new UnknownCommand();
        commands.forEach(this::registerCommand);
    }

    public void registerCommand(Command command) {
        if (commands.containsKey(command.getName().toLowerCase())) {
            throw new IllegalArgumentException("Command already registered: " + command.getName());
        }
        commands.put(command.getName().toLowerCase(), command);
    }

    public Command getCommand(String name) {
        return commands.getOrDefault(name.toLowerCase(), unknownCommand);
    }

    public Collection<Command> getAllCommands() {
        return commands.values();
    }
}