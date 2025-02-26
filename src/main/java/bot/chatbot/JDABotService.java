package bot.chatbot;

import net.dv8tion.jda.api.JDA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JDABotService implements BotService {
    private static final Logger logger = LoggerFactory.getLogger(JDABotService.class);
    private final JDA jda;

    public JDABotService(JDA jda) {
      this.jda = jda;
    }

    public JDA getJDA() {
        return jda;
    }

    @Override
    public void start(){
        try {
            jda.awaitReady();
            logger.info("Discord bot is ready!");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("JDA initialization was interrupted", e);
        }
    }

    @Override
    public void stop(){
        jda.shutdown();
        logger.info("Discord bot stopped.");
    }
}
