package bot.bot.commands.impl;

import bot.bot.commands.BaseCommand;
import bot.bot.commands.CommandConfig;
import bot.bot.messages.CommandContext;
import bot.bot.commands.Status;

public class HelpCommand extends BaseCommand {

    @Override
    public String getName() {
        return CommandConfig.getInstance().commands.get("help").name;
    }

    @Override
    public boolean isAvailableInStatus(Status status) { return true; }

    @Override
    public void execute(CommandContext context){
        CommandConfig.CommandEntry helpCommand = CommandConfig.getInstance().commands.get("help");

        String statusName = context.getStatus().getName();

        switch (statusName) {
            case "INACTIVE":
                context.getMessageSender()
                        .sendMessage(helpCommand.messages.inactive);
                break;

            case "ACTIVE":

                context.getMessageSender()
                        .sendMessage(helpCommand.messages.active);
                break;

            default:
                context.getMessageSender()
                        .sendMessage(CommandConfig.getInstance().commands.get("unknown").message);
                break;
        }

    }

    @Override
    public Status getNewStatus() {return null; }
}
