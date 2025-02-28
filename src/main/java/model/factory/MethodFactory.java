package model.factory;

import model.dao.MethodDAO;
import model.entities.Method;
import model.entities.Ticker;

import java.sql.SQLException;

public class MethodFactory {
    private final MethodDAO methodDAO;

    public MethodFactory(MethodDAO methodDAO) {
        this.methodDAO = methodDAO;
    }

    public Method createMethod(Ticker ticker, String methodName) throws SQLException {
        // Дополнительную логику или валидацию можно вставить здесь
        return methodDAO.saveMethod(ticker, methodName);
    }
}
