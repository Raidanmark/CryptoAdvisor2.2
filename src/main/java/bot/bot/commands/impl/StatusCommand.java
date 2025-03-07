package bot.bot.commands.impl;

import bot.bot.commands.BaseCommand;
import bot.bot.commands.CommandConfig;
import bot.bot.messages.CommandContext;
import bot.bot.commands.Status;

public class StatusCommand extends BaseCommand {


    @Override
    public String getName() {
        return CommandConfig.getInstance().commands.get("status").name ;
    }

    @Override
    public boolean isAvailableInStatus(Status status) {
        return status.getName().equals(CommandConfig.getInstance().commands.get("status").available_in_status);
    }

    @Override
    public void execute(CommandContext context) {
        context.getMessageSender().sendMessage(CommandConfig.getInstance().commands.get("status").message);
    }

    @Override
    public Status getNewStatus() {
        return new Status(CommandConfig.getInstance().commands.get("status").new_status);
    }

}
