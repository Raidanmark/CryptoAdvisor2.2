package model.factory;

import model.dao.MethodSetDAO;
import model.entities.Method;
import model.entities.MethodSet;

import java.sql.SQLException;
import java.util.List;

public class MethodSetFactory {
    private final MethodSetDAO methodSetDAO;

    public MethodSetFactory(MethodSetDAO methodSetDAO) {
        this.methodSetDAO = methodSetDAO;
    }

    public MethodSet createMethodSet(Method method, List<String> parameters) throws SQLException {
        return methodSetDAO.saveMethodSet(method, parameters);
    }
}
