package model.factory;

import model.dao.TimeframeDAO;
import model.entities.Ticker;
import model.entities.Timeframe;

import java.sql.SQLException;
import java.util.List;

public class TimeframeFactory {
    private final TimeframeDAO timeframeDAO;

    public TimeframeFactory(TimeframeDAO timeframeDAO) {
        this.timeframeDAO = timeframeDAO;
    }

    public Timeframe createTimeframe(Ticker ticker, String tfName) throws SQLException {
        // Создаём и сразу сохраняем:
        return timeframeDAO.saveTimeframe(ticker, tfName);
    }

    // Или метод, создающий несколько таймфреймов
    public void createTimeframes(Ticker ticker, List<String> timeframeNames) throws SQLException {
        for (String tfName : timeframeNames) {
            timeframeDAO.saveTimeframe(ticker, tfName);
        }
    }
}
