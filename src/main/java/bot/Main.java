package bot;

import bot.bottype.discord.BotListener;
import bot.chatbot.BotCore;
import bot.chatbot.BotService;
import bot.commands.CommandRegistry;
import bot.config.Config;
import bot.data.Data;
import bot.data.DataCollecting;
import bot.data.TickerRepository;
import bot.factory.*;
import bot.factory.bot.BotFactory;
import bot.factory.bot.DiscordBotFactory;
import model.entities.*;
import model.factory.DAOFactory;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {


            // Создаём конфиг
            Config config = new Config();

            //Создавать тут все конфиги



            Connection connection = DriverManager.getConnection(
                    config.getDBRoot(),
                    config.getDBUser(),
                    config.getDBPassword()
            );

            DAOFactory daoFactory = new DAOFactory(connection);


            for (Ticker ticker : allTickers) {
                for (Timeframe tf : allTimeframes) {
                    //TODO: Here full candles
                    for (Method m : allMethods) {
                        for (MethodSet ms : allSetsForMethod(m)) {
                            // Здесь создаём Signal
                            Signal sig = signalFactory.createSignal(false);
                            // При необходимости связываем MethodSet с Signal
                            // ...
                        }
                    }
                }
            }


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