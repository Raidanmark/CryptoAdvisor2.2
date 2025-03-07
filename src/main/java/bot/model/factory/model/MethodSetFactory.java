package bot.model.factory.model;

import bot.model.dao.MethodSetDAO;
import bot.model.entities.Method;
import bot.model.entities.MethodSet;
import bot.model.factory.AutoFactory;

import java.sql.SQLException;
import java.util.List;

@AutoFactory(eager = true)
public class MethodSetFactory {
    private final MethodSetDAO methodSetDAO;

    public MethodSetFactory(MethodSetDAO methodSetDAO) {
        this.methodSetDAO = methodSetDAO;
    }

    public MethodSet createMethodSet(Method method, List<String> parameters) throws SQLException {
        return methodSetDAO.saveMethodSet(method, parameters);
    }
}
