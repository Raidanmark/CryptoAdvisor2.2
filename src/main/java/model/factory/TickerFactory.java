package model.factory;

import model.dao.TickerDAO;
import model.entities.Ticker;

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
