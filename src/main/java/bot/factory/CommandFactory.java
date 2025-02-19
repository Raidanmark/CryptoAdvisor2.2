package bot.factory;

import bot.commands.Command;
import bot.commands.CommandRegistry;
import bot.commands.impl.HelpCommand;
import bot.commands.impl.StartCommand;
import bot.commands.impl.StatusCommand;
import bot.commands.impl.StopCommand;
import bot.data.TickerRepository;

import java.util.List;

public class CommandFactory {
    public static CommandRegistry createCommandRegistry(TickerRepository tickerRepository) {
        List<Command> commands = List.of(
                new HelpCommand(),
                new StartCommand(),
                new StopCommand(),
                new StatusCommand(tickerRepository)
        );
        return new CommandRegistry(commands);
    }
}