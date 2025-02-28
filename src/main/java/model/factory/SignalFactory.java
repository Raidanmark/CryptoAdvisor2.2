package model.factory;

import model.dao.SignalDAO;
import model.entities.MethodSet;
import model.entities.Signal;
import model.entities.Timeframe;

import java.sql.SQLException;
import java.util.List;

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
