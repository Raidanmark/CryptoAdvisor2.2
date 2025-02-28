package model.factory;

import model.dao.CandleDAO;
import model.entities.Candle;
import model.entities.Timeframe;

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
