package bot.model.factory.model;

import bot.model.dao.SignalDAO;
import bot.model.entities.MethodSet;
import bot.model.entities.Timeframe;
import bot.model.factory.AutoFactory;

import java.sql.SQLException;
import java.util.List;

@AutoFactory
public class SignalFactory {
    private final SignalDAO signalDAO;

    public SignalFactory(SignalDAO signalDAO) {
        this.signalDAO = signalDAO;
    }

    public void createSignals(List<MethodSet> methodSets, List<Timeframe> timeframes) throws SQLException {
        for (MethodSet methodSet : methodSets) {
            for (Timeframe timeframe : timeframes) {
                signalDAO.saveSignal(true, methodSet, timeframe);
            }
        }
    }
}
