package bot;

import bot.bot.bottype.discord.BotListener;
import bot.bot.chatbot.BotCore;
import bot.bot.chatbot.BotService;
import bot.bot.commands.CommandFactory;
import bot.bot.commands.CommandRegistry;
import bot.bot.config.Config;
import bot.bot.chatbot.BotFactory;
import bot.bot.bottype.discord.DiscordBotFactory;
import bot.model.entities.Ticker;
import bot.model.factory.DAOFactory;
import bot.model.factory.FactoryProvider;
import bot.model.factory.model.TickerFactory;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {

            FactoryProvider.finishInitialization();

            // Create config
            Config config = new Config();

            //Connection to DB
            Connection connection = DriverManager.getConnection(
                    config.getDBRoot(),
                    config.getDBUser(),
                    config.getDBPassword()
            );



            // Commands initialization
            CommandRegistry commandRegistry = CommandFactory.createCommandRegistry();
            // Creating Discord BotListener
            BotListener botListener = new BotListener(commandRegistry);
            // Creating JDA
            JDA jda = DiscordBotFactory.createJDA(config.getDiscordToken(), botListener);

            // Bot creating and launching
            BotService botService = BotFactory.createBot("discord", jda);
            BotCore botCore = new BotCore(botService);
            botCore.start();

            List<String> tickers = List.of("wftpq", "tpwq", "ssc");
            FactoryProvider.getFactory(DAOFactory.class);
            Ticker ticker = FactoryProvider.getFactory(TickerFactory.class).createTicker("BTC");



/*
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
*/






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