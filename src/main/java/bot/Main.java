package bot;

import bot.analytics.TickerAnalyzer;
import bot.bottype.discord.BotListener;
import bot.chatbot.BotCore;
import bot.chatbot.BotService;
import bot.commands.CommandRegistry;
import bot.config.Config;
import bot.data.Data;
import bot.data.DataCollecting;
import bot.data.TickerRepository;
import bot.factory.*;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {


            // Создаём конфиг
            Config config = new Config();

            //Создавать тут все конфиги


            // Data storage
            TickerRepository tickerRepository = TickerStorageFactory.createTickerRepository("memory");
            // Commands initialization
            CommandRegistry commandRegistry = CommandFactory.createCommandRegistry(tickerRepository);
            // Creating data agregation module
            DataCollecting dataCollecting = DataCollectingFactory.create(tickerRepository);
            Data data = new Data(tickerRepository, dataCollecting);




            // Создаём Discord BotListener
            BotListener botListener = new BotListener(commandRegistry);
            // Создаём JDA через фабрику
            JDA jda = DiscordBotFactory.createJDA(config.getDiscordToken(), botListener);

            // 5️⃣ Создаём и запускаем бота
            BotService botService = BotFactory.createBot("discord", jda);
            BotCore botCore = new BotCore(botService);
            botCore.start();
            data.start();


            logger.info("App successfully started!");

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("Shutting down...");
                botCore.stop();
                logger.info("App successfully stopped!");
            }));

        } catch (Exception e) {
            logger.error("Initializing app error: " + e);
        }
    }
}