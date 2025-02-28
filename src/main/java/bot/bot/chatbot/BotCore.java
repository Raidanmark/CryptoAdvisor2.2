package bot.bot.chatbot;


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



