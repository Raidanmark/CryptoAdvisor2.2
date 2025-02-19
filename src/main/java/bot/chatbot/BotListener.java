package bot.chatbot;

import bot.commands.CommandRegistry;
import bot.messages.JDAMessageSender;
import bot.messages.MessageSender;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class BotListener extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(BotListener.class);

    private final Map<String, ChatBotSession> chatSessions = new HashMap<>();
    private final CommandRegistry commandRegistry;

    public BotListener(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (shouldIgnore(event)) {
            return;
        }

        String chatId = event.getChannel().getId();
        String message = event.getMessage().getContentRaw();

        logger.info("[Message Received] Chat ID: {}, Message: {}", chatId, message);

        try {
            ChatBotSession session = getOrCreateSession(chatId, event);
            session.processCommand(message);
        } catch (Exception e) {
            logger.error("Error processing message: {}", message, e);
        }
    }

    private boolean shouldIgnore(MessageReceivedEvent event) {
        return event.getAuthor().isBot();
    }

    private ChatBotSession getOrCreateSession(String chatId, MessageReceivedEvent event) {
        return chatSessions.computeIfAbsent(chatId, id -> {
            logger.info("Creating new session for chat ID: {}", chatId);
            MessageSender messageSender = new JDAMessageSender(event.getChannel());
            return new ChatBotSession(messageSender, commandRegistry);
        });
    }

    public void broadcastMessage(String message) {
        chatSessions.values().forEach(session -> {
            if ("ACTIVE".equals(session.getCurrentStatus().getName())) {
                try {
                    session.getMessageSender().sendMessage(message);
                    logger.info("[Broadcast] Message sent: {}", message);
                } catch (Exception e) {
                    logger.error("Failed to send broadcast message: {}", message, e);
                }
            }
        });
    }
}
