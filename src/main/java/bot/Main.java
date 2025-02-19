package bot;

import bot.analytics.TickerAnalyzer;
import bot.chatbot.BotCore;
import bot.chatbot.BotListener;
import bot.chatbot.BotService;
import bot.chatbot.ChatBotSession;
import bot.commands.CommandRegistry;
import bot.config.Config;
import bot.data.Data;
import bot.data.TickerRepository;
import bot.factory.BotFactory;
import bot.factory.CommandFactory;
import bot.factory.DiscordBotFactory;
import bot.factory.TickerStorageFactory;
import net.dv8tion.jda.api.JDA;


public class Main {
    public static void main(String[] args) {
        try {


            // 1️⃣ Создаём конфиг
            Config config = new Config();

            // 2️⃣ Создаём CommandRegistry

            TickerRepository tickerRepository = TickerStorageFactory.createTickerRepository("memory");
            CommandRegistry commandRegistry = CommandFactory.createCommandRegistry(tickerRepository);

            // 3️⃣ Создаём BotListener
            BotListener botListener = new BotListener(commandRegistry);


            // 4️⃣ Создаём JDA через фабрику
            JDA jda = DiscordBotFactory.createJDA(config.getDiscordToken(), botListener);

            // 5️⃣ Создаём и запускаем бота
            BotService botService = BotFactory.createBot("discord", jda);
            BotCore botCore = new BotCore(botService);
            botCore.start();


            System.out.println("App successfully started!");
        } catch (Exception e) {
            System.err.println("Initializing app error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}