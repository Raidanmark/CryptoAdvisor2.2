package bot.bot.messages;

import bot.bot.commands.Status;

public class CommandContext {
    private final MessageSender messageSender;
    private final Status status;

    public CommandContext(MessageSender messageSender, Status status) {
        this.messageSender = messageSender;
        this.status = status;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    public Status getStatus() {
        return status;
    }
}
