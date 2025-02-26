package bot.data;

import bot.analytics.TickerAnalyzer;
import bot.data.model.Ticker;

import java.util.ArrayList;
import java.util.List;

public class TickerRepository {
    private  TickerAnalyzer tickerAnalyzer;
    private final TickerStorage storage;

    public TickerRepository(TickerStorage storage) {
        this.storage = storage;
    }

    public void setTickerAnalyzer(TickerAnalyzer tickerAnalyzer) {
        this.tickerAnalyzer = tickerAnalyzer;
    }

    public void addTickers(List<Ticker> newTickers) {
        storage.addTickers(newTickers);
    }

    public List<Ticker> getAllTickers() {
        return storage.getAllTickers();
    }

    public Ticker findTicker(String symbol, String timeframe) {
        return storage.findTicker(symbol, timeframe);
    }

    public void updateTicker(Ticker updatedTicker) {
        storage.updateTicker(updatedTicker);
    }

    public void analyzeAllTickers() {
        getAllTickers().forEach(tickerAnalyzer::analyzeTicker);
    }

    public void analyzeUpdatedTicker(Ticker updatedTicker) {
        tickerAnalyzer.analyzeTicker(updatedTicker);
    }
}
