package bot.bot.commands;

import bot.bot.messages.CommandContext;

public interface Command {
    String getName();
    boolean isAvailableInStatus(Status status); // Проверяет, можно ли выполнить команду в данном статусе
    void execute(CommandContext context);       // Выполняет команду
    Status getNewStatus();
}



