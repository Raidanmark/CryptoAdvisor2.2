package bot.data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import bot.config.CandleFilter;
import bot.config.DataConfig;
import bot.data.api.ApiClient;
import bot.data.api.Websocket;
import bot.data.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataCollecting {
    private final ApiClient apiClient;
    private final Websocket websocket;
    private final TickerRepository tickerRepository;
    private final DataConfig dataConfig;
    private final CandleFilter candleFilter;
    private static final Logger logger = LoggerFactory.getLogger(DataCollecting.class);


    //TODO: Creating by factory
    public DataCollecting(ApiClient apiClient, Websocket websocket, TickerRepository tickerRepository, DataConfig dataConfig, CandleFilter candleFilter) {
        this.apiClient = apiClient;
        this.websocket = websocket;
        this.tickerRepository = tickerRepository;
        this.dataConfig = dataConfig;
        this.candleFilter = candleFilter;


    }

    //FIXME: All lifecycle of class executing here
    public List<Ticker> start() {
        try {

            List<MarketData> marketData = fetchMarketData();
            List<DOTMarketData> sortedTickers = sortAndLimitTickers(marketData);
            return fetchCandlestickData(sortedTickers);

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Error fetching tickers", e);
        }
    }

    private List<MarketData> fetchMarketData() throws IOException, URISyntaxException {
        return apiClient.marketTickers(Collections.emptyMap());
    }

    //Creating sorted tickers list
    //Logically after this should be created ticker factory
    private List<DOTMarketData> sortAndLimitTickers(List<MarketData> marketData) {
        return marketData.stream()
                .map(data -> new DOTMarketData(data.symbol(), data.vol()))
                .sorted(Comparator.comparingDouble(DOTMarketData::vol).reversed())
                .limit(dataConfig.getAmountOfCryptocurrency())
                .collect(Collectors.toList());
    }

    //FIXME: Call fetch klines for all ticker (with timeframe). Cycle should be as method from another place (fx ticker navigation)
    private List<Ticker> fetchCandlestickData(List<DOTMarketData> tickers) {
        List<Ticker> allTickers = new ArrayList<>();
        for (DOTMarketData ticker : tickers) {
            for (String timeframe : dataConfig.getTimeframes()) {
                try {
                    List<Kline> klineData = fetchKlines(ticker, timeframe);
                    allTickers.add(createTicker(ticker, timeframe, klineData));
                    setupWebsocketUpdates(ticker.symbol(), timeframe);
                } catch (IOException | URISyntaxException e) {
                    logger.error("Error fetching candles for ticker: " + ticker.symbol(), e);
                }
            }
        }
        return allTickers;
    }

    //FIXME: Candles amount should come from another place
    private List<Kline> fetchKlines(DOTMarketData ticker, String timeframe) throws IOException, URISyntaxException {
        Map<String, String> parameters = Map.of(
                "period", timeframe,
                "size", String.valueOf(dataConfig.getCandlesAmount()),
                "symbol", ticker.symbol()
        );
        return apiClient.getKlines(parameters);
    }

    //Util for fetchCandlestickData
    private void setupWebsocketUpdates(String symbol, String timeframe) {
        websocket.updateCandlestick(symbol, timeframe, this::handleNewCandlestick);
    }

    //FIXME: Should be created by factory and called from main file or same
    private Ticker createTicker(DOTMarketData ticker, String timeframe, List<Kline> klineData) {
        LinkedList<Double> closePrices = klineData.stream()
                .map(Kline::close)
                .collect(Collectors.toCollection(LinkedList::new));

        long lastTimestamp =  System.currentTimeMillis();;
        return new Ticker(ticker.symbol(), timeframe, closePrices, lastTimestamp, false, false);
    }


    // Handles data updates via WebSocket
    protected void handleNewCandlestick(Kline kline, String channel, long timestamp) {
        String[] parts = channel.split("\\.");
        String symbol = parts[1];
        String timeframe = parts[3];

        // Find the ticker in the repository
        //FIXME: Should be used method from another file (fx ticker navigation)
        Ticker ticker = tickerRepository.findTicker(symbol, timeframe);
        if (ticker != null) {
            // Check whether it is necessary to update ticker data
            //FIXME: It should be another class with methods for this
            long adjustedTimestamp = ticker.lastTimestamp() + candleFilter.getIntervalDuration(timeframe);

            if (timestamp > adjustedTimestamp) {
                ticker.addClosePrice(kline.close(), dataConfig.getCandlesAmount());
                Ticker updatedTicker = new Ticker(
                        symbol, timeframe, ticker.close(), timestamp, ticker.SMAsignal(), ticker.MACDsignal()
                );
                tickerRepository.updateTicker(updatedTicker);
                tickerRepository.analyzeUpdatedTicker(updatedTicker);

                logger.info("Тикер обновлён: " + symbol + " (" + timeframe + ")");

            } else {}
        } else {
            logger.error("Тикер не найден: " + symbol + " и таймфрейм: " + timeframe);
        }
    }
}
