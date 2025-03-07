package bot.bot.commands.impl;

import bot.bot.commands.BaseCommand;
import bot.bot.commands.CommandConfig;
import bot.bot.messages.CommandContext;
import bot.bot.commands.Status;


public class UnknownCommand extends BaseCommand {

    @Override
    public String getName() {
        return CommandConfig.getInstance().commands.get("unknown").name;
    }

    @Override
    public boolean isAvailableInStatus(Status status) {
        return status.getName().equals(CommandConfig.getInstance().commands.get("unknown").available_in_status);
    }

    @Override
    public void execute(CommandContext context) {
        context.getMessageSender().sendMessage(CommandConfig.getInstance().commands.get("unknown").message);
    }

    @Override
    public Status getNewStatus() {
        return new Status(CommandConfig.getInstance().commands.get("unknown").new_status);
    }
}
