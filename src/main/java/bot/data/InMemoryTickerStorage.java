package bot.data;

import bot.data.model.Ticker;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTickerStorage implements TickerStorage {
    private final List<Ticker> tickers = new ArrayList<>();

    @Override
    public void addTickers(List<Ticker> newTickers) {
        tickers.addAll(newTickers);
    }

    @Override
    public List<Ticker> getAllTickers() {
        return new ArrayList<>(tickers); // Возвращаем копию списка для защиты от изменений
    }

    @Override
    public Ticker findTicker(String symbol, String timeframe) {
        return tickers.stream()
                .filter(t -> t.symbol().equals(symbol) && t.timeframe().equals(timeframe))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateTicker(Ticker updatedTicker) {
        for (int i = 0; i < tickers.size(); i++) {
            Ticker existingTicker = tickers.get(i);
            if (existingTicker.symbol().equals(updatedTicker.symbol()) &&
                    existingTicker.timeframe().equals(updatedTicker.timeframe())) {
                tickers.set(i, updatedTicker); // Обновляем тикер
                return;
            }
        }
        throw new IllegalArgumentException("Ticker not found: " + updatedTicker.symbol() + " " + updatedTicker.timeframe());
    }
}
