package bot.data;


import bot.data.model.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Data {
    private final TickerRepository tickerRepository;
    private final DataCollecting dataCollecting;
    private static final Logger logger = LoggerFactory.getLogger(Data.class);

    public Data(TickerRepository tickerRepository, DataCollecting dataCollecting) {
        this.tickerRepository = tickerRepository;
        this.dataCollecting = dataCollecting;
    }


    public void start() {
        loadTickers();
        analyzeTickers();
    }


    private void loadTickers() {
        List<Ticker> initialTickers = dataCollecting.start();
        tickerRepository.addTickers(initialTickers);
        logger.info("Tickers loaded: " + tickerRepository.getAllTickers());
    }

    private void analyzeTickers() {
        tickerRepository.analyzeAllTickers();
    }

    public void updateTicker(Ticker updatedTicker) {
        tickerRepository.updateTicker(updatedTicker);
    }
}

