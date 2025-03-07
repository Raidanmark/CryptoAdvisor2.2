package bot.model.factory.model;

import bot.model.dao.CandleDAO;
import bot.model.entities.Candle;
import bot.model.factory.AutoFactory;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

@AutoFactory
public class CandleFactory {
    private final CandleDAO candleDAO;

    public CandleFactory(CandleDAO candleDAO) {
        this.candleDAO = candleDAO;
    }

    public static Candle createCandle(String ticker, String timeframe, double openPrice, double highPrice, double lowPrice, double closePrice, double volume) {
        long timestamp = Instant.now().getEpochSecond();
        return Candle.create(ticker, timeframe, openPrice, highPrice, lowPrice, closePrice, volume, timestamp);
    }

    // Сохранение списка свечей в БД
    public void saveCandles(List<Candle> candles) throws SQLException {
        for (Candle candle : candles) {
            candleDAO.saveCandle(candle);
        }
    }
}
