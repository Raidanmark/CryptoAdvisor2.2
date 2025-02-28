package bot.bot.commands.impl;

import bot.bot.commands.BaseCommand;
import bot.bot.messages.CommandContext;
import bot.bot.commands.Status;

public class StatusCommand extends BaseCommand {


    @Override
    public String getName() {
        return "!Status"; // Имя команды
    }

    @Override
    public boolean isAvailableInStatus(Status status) {
        return status.getName().equals("ACTIVE"); // Доступность команды в определённом статусе
    }

    @Override
    public void execute(CommandContext context) {    }

    @Override
    public Status getNewStatus() {
        return null; // Если команда не изменяет статус, возвращайте null
    }

}
