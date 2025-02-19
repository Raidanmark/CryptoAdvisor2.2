package bot.factory;

import bot.data.InMemoryTickerStorage;
import bot.data.TickerRepository;
import bot.data.TickerStorage;

public class TickerStorageFactory {
    public static TickerRepository createTickerRepository(String storgeType) {
        TickerStorage starage;
        switch (storgeType.toLowerCase()){
            case "memory":
                starage = new InMemoryTickerStorage();
                break;

            case "database":
                throw new UnsupportedOperationException("Database storage is not imlemented yet!");

                default:
                    throw new IllegalArgumentException("Unknown storge type: " + storgeType);
        }
        return new TickerRepository(starage);
    }
}
