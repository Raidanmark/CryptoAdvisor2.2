package bot.model.factory;

import bot.model.dao.CandleDAO;
import bot.model.entities.Candle;

import java.sql.SQLException;
import java.util.List;

public class CandleFactory {
    private final CandleDAO candleDAO;

    public CandleFactory(CandleDAO candleDAO) {
        this.candleDAO = candleDAO;
    }

    // Сохранение списка свечей в БД
    public void saveCandles(List<Candle> candles) throws SQLException {
        for (Candle candle : candles) {
            candleDAO.saveCandle(candle);
        }
    }
}
