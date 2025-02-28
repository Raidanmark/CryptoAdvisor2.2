package bot.factory;

import bot.config.CandleFilter;
import bot.config.DataConfig;
import bot.data.*;
import bot.data.api.ApiClient;
import bot.data.api.Websocket;
import bot.factory.huobi.HuobiApiFactory;

public class DataCollectingFactory {
    public static DataCollecting create(TickerRepository tickerRepository) {
        ApiClient apiClient = HuobiApiFactory.createREST(tickerRepository);
        Websocket websocket = HuobiApiFactory.createWebsocket();
        DataConfig dataConfig = new DataConfig();
        CandleFilter candleFilter = new CandleFilter();

        return new DataCollecting(apiClient, websocket, tickerRepository, dataConfig, candleFilter);
    }
}
