package bot.chatbot;

import net.dv8tion.jda.api.JDA;


public class BotCore {
    private final BotService botService;

    public BotCore(BotService botService) {
        this.botService = botService;
    }

    public void start(){
        botService.start();
    }

    public void stop(){
        botService.stop();
    }

}



