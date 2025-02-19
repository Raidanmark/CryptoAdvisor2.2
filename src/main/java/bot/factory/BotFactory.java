package bot.factory;

import bot.chatbot.BotListener;
import bot.chatbot.BotService;
import bot.chatbot.JDABotService;
import bot.config.Config;
import net.dv8tion.jda.api.JDA;

public class BotFactory {
    public static BotService createBot(String type, JDA jda){
        switch (type.toLowerCase()){
            case "discord":
                return new JDABotService(jda);
                default:
                    throw new IllegalArgumentException("Unknown bot type: " + type);
        }
    }
}