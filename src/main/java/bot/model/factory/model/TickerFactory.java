package bot.model.factory.model;

import bot.model.dao.TickerDAO;
import bot.model.entities.Ticker;
import bot.model.factory.AutoFactory;
import bot.model.factory.DAOFactory;
import bot.model.factory.FactoryProvider;

import java.sql.SQLException;

@AutoFactory(eager = true)
public class TickerFactory {
    private final TickerDAO tickerDAO = FactoryProvider.getFactory(DAOFactory.class).getDAO(TickerDAO.class);

    public Ticker createTicker(String name) throws SQLException {
        Ticker ticker = new Ticker(name);
        tickerDAO.saveTicker(ticker);
        Long id = tickerDAO.findIdByName(name);
        ticker.setId(id);
        return ticker;

    }
}
