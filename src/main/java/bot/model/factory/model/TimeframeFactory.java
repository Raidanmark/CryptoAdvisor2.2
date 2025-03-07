package bot.model.factory.model;

import bot.model.dao.TimeframeDAO;
import bot.model.entities.Ticker;
import bot.model.entities.Timeframe;
import bot.model.factory.AutoFactory;

import java.sql.SQLException;
import java.util.List;

@AutoFactory
public class TimeframeFactory {
    private final TimeframeDAO timeframeDAO;

    public TimeframeFactory(TimeframeDAO timeframeDAO) {
        this.timeframeDAO = timeframeDAO;
    }

    public Timeframe createTimeframe(Ticker ticker, String tfName) throws SQLException {
        // Создаём и сразу сохраняем:
        return timeframeDAO.saveTimeframe(ticker, tfName);
    }


}
