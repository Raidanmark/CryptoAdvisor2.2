package bot.bot.commands;

import bot.bot.commands.impl.HelpCommand;
import bot.bot.commands.impl.StartCommand;
import bot.bot.commands.impl.StatusCommand;
import bot.bot.commands.impl.StopCommand;

import java.util.List;

public class CommandFactory {
    public static CommandRegistry createCommandRegistry() {
        List<Command> commands = List.of(
                new HelpCommand(),
                new StartCommand(),
                new StopCommand(),
                new StatusCommand()
        );
        return new CommandRegistry(commands);
    }
}