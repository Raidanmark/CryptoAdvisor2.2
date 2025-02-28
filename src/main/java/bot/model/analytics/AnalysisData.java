package bot.model.analytics;

import bot.model.entities.Candle;

import java.util.List;
import java.util.stream.Collectors;

public class AnalysisData {
    private final String symbol;
    private final String timeframe;
    private final List<Candle> candles;

    public AnalysisData(String symbol, String timeframe, List<Candle> candles) {
        this.symbol = symbol;
        this.timeframe = timeframe;
        this.candles = candles;
    }

    public String getSymbol() { return symbol; }
    public String getTimeframe() { return timeframe; }
    public List<Candle> getCandles() { return candles; }

    // Доступные методы для получения нужных данных
    public List<Double> getClosePrices() {
        return candles.stream().map(Candle::getClosePrice).collect(Collectors.toList());
    }

    public List<Double> getHighPrices() {
        return candles.stream().map(Candle::getHighPrice).collect(Collectors.toList());
    }

    public List<Double> getLowPrices() {
        return candles.stream().map(Candle::getLowPrice).collect(Collectors.toList());
    }

    public List<Double> getVolumes() {
        return candles.stream().map(Candle::getVolume).collect(Collectors.toList());
    }
}