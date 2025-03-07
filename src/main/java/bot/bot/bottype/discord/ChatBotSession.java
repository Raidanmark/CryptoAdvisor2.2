package bot.bot.bottype.discord;

import bot.bot.commands.Command;
import bot.bot.commands.CommandRegistry;

import bot.bot.messages.CommandContext;
import bot.bot.messages.MessageSender;
import bot.bot.commands.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatBotSession {

    private Status currentStatus;
    private final MessageSender messageSender;
    private final CommandRegistry commandRegistry;
    private static final Logger logger = LoggerFactory.getLogger(ChatBotSession.class);

    public ChatBotSession(MessageSender messageSender, CommandRegistry commandRegistry) {
        this.messageSender = messageSender;
        this.commandRegistry = commandRegistry;
        this.currentStatus = new Status("INACTIVE"); // Начальный статус
    }

    // Method for processing commands
    public void processCommand(String commandText) {
        if (!isCommand(commandText)) {
            handleNonCommandMessage(commandText);
            return;
        }

        //Check is command exist
        if(!isCommandExists(commandText)) {
            handleUnexistableCommand(commandText);
            return;
        }


        Command command = commandRegistry.getCommand(commandText);
        if (!isCommandAvailableInStatus(command)) {
            handleUnavailableCommand(commandText);
            return;
        }

        executeCommand(command);
    }

    private void handleUnexistableCommand(String commandText) {
        messageSender.sendMessage("<" + commandText + "> is unavailable command. Write <<!Help>> to get more information about the commands.");
    }

    private boolean isCommandExists(String commandText) {
        return commandRegistry.getAllCommands().stream()
                .anyMatch(command -> command.getName().equalsIgnoreCase(commandText));
    }

    private boolean isCommand(String commandText) {
        return commandText.startsWith("!");
    }

    private void handleNonCommandMessage(String message) {
        logger.info("Received non-command message: " + message);
    }

    private boolean isCommandAvailableInStatus(Command command) {
        return command != null && command.isAvailableInStatus(currentStatus);
    }

    private void handleUnavailableCommand(String commandText) {
        messageSender.sendMessage("Command <" + commandText + "> is not available in the current status <" + currentStatus.getName() + ">. Write <<!Help>> to get more information about the bot.");
    }

    private void executeCommand(Command command) {
        try {
            CommandContext context = new CommandContext(messageSender, currentStatus);
            command.execute(context);
            updateStatus(command);
        } catch (Exception e) {
            logger.error("Error executing command: " + command.getName(), e);
        }
    }

    private void updateStatus(Command command) {
        Status newStatus = command.getNewStatus();
        if (newStatus != null) {
            logger.info("Updating status from " + currentStatus.getName() + " to " + newStatus.getName());
            currentStatus = newStatus;
        }
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    private void log(String message) {
        logger.info("[ChatBotSession] " + message);
    }

    private void logError(String message, Throwable e) {
        logger.error("[ChatBotSession] " + message, e);
    }
}
