package bot.bot.commands.impl;

import bot.bot.commands.BaseCommand;
import bot.bot.commands.CommandConfig;
import bot.bot.messages.CommandContext;
import bot.bot.commands.Status;

public class StartCommand extends BaseCommand {

    @Override
    public String getName() {
        return CommandConfig.getInstance().commands.get("start").name;
    }

    @Override
    public boolean isAvailableInStatus(Status status) {
        return status.getName().equals(CommandConfig
                .getInstance().commands.get("start").available_in_status);
    }

    @Override
    public void execute(CommandContext context) {
        context.getMessageSender()
                .sendMessage(CommandConfig.getInstance().commands.get("start").message);
    }

    @Override
    public Status getNewStatus() {
        return new Status(CommandConfig.getInstance().commands.get("start").new_status);
    }
}
