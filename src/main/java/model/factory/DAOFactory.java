package model.factory;

import model.dao.BaseDAO;
import org.reflections.Reflections;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DAOFactory {
    private final Connection connection;
    private final Map<Class<?>, Object> daoCache = new HashMap<>();

    public DAOFactory(Connection connection) {
        this.connection = connection;
        registerAllDAOs("model.dao");  // пакет, где лежат DAO-классы
    }

    public void registerAllDAOs(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends BaseDAO>> daoClasses = reflections.getSubTypesOf(BaseDAO.class);

        for (Class<? extends BaseDAO> daoClass : daoClasses) {
            try {
                // Проверяем, есть ли нужный конструктор
                Constructor<? extends BaseDAO> constructor = daoClass.getConstructor(Connection.class);

                // Создаём объект
                BaseDAO daoInstance = constructor.newInstance(connection);

                // Сохраняем в кэше
                daoCache.put(daoClass, daoInstance);

            } catch (NoSuchMethodException e) {
                System.err.println("Пропущен DAO-класс (нет конструктора Connection): " + daoClass.getName());
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при создании DAO: " + daoClass, e);
            }
        }

    }

    @SuppressWarnings("unchecked")
    public <T extends BaseDAO> T getDAO(Class<T> clazz) {
        return (T) daoCache.get(clazz);
    }
}

