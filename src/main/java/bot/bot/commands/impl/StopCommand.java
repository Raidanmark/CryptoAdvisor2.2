package bot.bot.commands.impl;

import bot.bot.commands.BaseCommand;
import bot.bot.commands.CommandConfig;
import bot.bot.messages.CommandContext;
import bot.bot.commands.Status;

public class StopCommand extends BaseCommand {

    @Override
    public String getName() {

        return CommandConfig.getInstance().commands.get("stop").name;
    }

    @Override
    public boolean isAvailableInStatus(Status status) {
        return status.getName().equals(CommandConfig.getInstance().commands.get("stop").available_in_status); // Команда доступна только в статусе ACTIVE
    }

    @Override
    public void execute(CommandContext context) {
        context.getMessageSender().sendMessage(CommandConfig.getInstance().commands.get("stop").message); // Выполняем команду
    }

    @Override
    public Status getNewStatus() {
        return new Status( CommandConfig.getInstance().commands.get("stop").new_status); // Устанавливаем новый статус после выполнения команды
    }
}