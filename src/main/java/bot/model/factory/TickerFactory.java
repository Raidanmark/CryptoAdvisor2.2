package bot.model.factory;

import bot.model.dao.TickerDAO;
import bot.model.entities.Ticker;

import java.sql.SQLException;

public class TickerFactory {
    private final TickerDAO tickerDAO;
    public TickerFactory(TickerDAO tickerDAO) {
        this.tickerDAO = tickerDAO;
    }
    public Ticker createTicker(String name) throws SQLException {
        return tickerDAO.saveTicker(name);
    }
}
