package bot.commands;

import bot.status.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(BaseCommand.class);
    @Override
    public boolean isAvailableInStatus(Status status) {
        return true; // Доступна по умолчанию, переопределяется в подклассах
    }

    @Override
    public Status getNewStatus() {
        return null; // Не меняет статус по умолчанию
    }

    protected void logExecution(String commandName) {
        logger.info("Executing command: " + commandName);
    }
}