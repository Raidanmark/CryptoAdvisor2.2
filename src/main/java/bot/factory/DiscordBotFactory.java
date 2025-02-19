package bot.factory;

import bot.chatbot.BotListener;
import bot.chatbot.JDABotService;
import bot.config.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DiscordBotFactory {
    private static final Logger logger = LoggerFactory.getLogger(DiscordBotFactory.class);

    public static JDA createJDA(String token, BotListener listener) {
        try {
            return JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(listener)
                    .build();
        } catch (Exception e) {
            logger.error("Error creating JDA: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating JDA", e);
        }
    }
}
