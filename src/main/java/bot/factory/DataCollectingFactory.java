package bot.factory;

import bot.config.DataConfig;
import bot.data.*;
import bot.data.api.ApiClient;
import bot.data.api.huobi.HuobiApi;
import org.apache.http.impl.client.HttpClients;

public class DataCollectingFactory {
    public static DataCollecting create(TickerRepository tickerRepository) {
        ApiClient apiClient = HuobiApiFactory.create(tickerRepository);
        Websocket websocket = new Websocket();
        DataConfig dataConfig = new DataConfig();
        CandleFilter candleFilter = new CandleFilter();

        return new DataCollecting(apiClient, websocket, tickerRepository, dataConfig, candleFilter);
    }
}
