package bot.bot.chatbot;


import bot.model.factory.AutoFactory;
import net.dv8tion.jda.api.JDA;

@AutoFactory
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