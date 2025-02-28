package bot.model.factory;

import bot.model.dao.MethodSetDAO;
import bot.model.entities.Method;
import bot.model.entities.MethodSet;

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
