package bot.commands.impl;

import bot.commands.BaseCommand;
import bot.messages.CommandContext;
import bot.status.Status;

import static bot.config.CommandResponse.*;

public class HelpCommand extends BaseCommand {

    @Override
    public String getName() {
        return "!help"; // Приведено к нижнему регистру для согласованности
    }

    @Override
    public boolean isAvailableInStatus(Status status) {
        return true; // Команда доступна во всех статусах
    }

    @Override
    public void execute(CommandContext context){
        String statusName = context.getStatus().getName();

        switch (statusName) {
            case "INACTIVE":
                context.getMessageSender().sendMessage(HELP_COMMAND_MESSAGE_INACTIVE);
                break;

            case "ACTIVE":

                context.getMessageSender().sendMessage(HELP_COMMAND_MESSAGE_ACTIVE);
                break;

            default:
                context.getMessageSender().sendMessage(UNKNOWN_STATUS_MESSAGE);
                break;
        }

    }

    @Override
    public Status getNewStatus() {
        return null; // Команда не меняет статус
    }
}
