package bot.data;

import bot.data.model.Ticker;

import java.util.List;

public interface TickerStorage {
    void addTickers(List<Ticker> newTickers);
    List<Ticker> getAllTickers();
    Ticker findTicker(String symbol, String timeframe);
    void updateTicker(Ticker updatedTicker);
}