package bot.bot.commands.impl;

import bot.bot.commands.BaseCommand;
import bot.bot.messages.CommandContext;
import bot.bot.commands.Status;

import static bot.bot.config.CommandResponse.UNKNOWN_COMMAND_MESSAGE;

public class UnknownCommand extends BaseCommand {

    @Override
    public String getName() {
        return "__unknown__"; // Приведено к нижнему регистру для согласованности
    }

    @Override
    public boolean isAvailableInStatus(Status status) {
        return true; // Доступна во всех статусах
    }

    @Override
    public void execute(CommandContext context) {
        context.getMessageSender().sendMessage(UNKNOWN_COMMAND_MESSAGE);
    }

    @Override
    public Status getNewStatus() {
        return null; // Команда не меняет статус
    }
}
