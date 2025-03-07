package bot.model.factory;

import bot.bot.bottype.discord.BotListener;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class FactoryProvider {
    private static final Map<Class<?>, Supplier<?>> factorySuppliers = new HashMap<>();
    private static final Map<Class<?>, Object> instances = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(FactoryProvider.class);
    private static boolean initialized = false;

    static {
        initializeFactories();
    }

    // 📌 Теперь просто регистрируем фабрики, но НЕ создаём их сразу
    private static void initializeFactories() {
        Reflections reflections = new Reflections("bot.model");
        Set<Class<?>> factoryClasses = reflections.getTypesAnnotatedWith(AutoFactory.class);

        for (Class<?> clazz : factoryClasses) {
            factorySuppliers.put(clazz, () -> createInstance(clazz));
        }

        logger.info("Все фабрики зарегистрированы, но не созданы.");
    }

    // 📌 Вызываем перед первым использованием фабрик
    public static void finishInitialization() {
        if (!initialized) {
            logger.info("Создаём фабрики, помеченные как eager...");
            for (Class<?> clazz : factorySuppliers.keySet()) {
                AutoFactory annotation = clazz.getAnnotation(AutoFactory.class);
                if (annotation != null && annotation.eager()) {
                    instances.put(clazz, createInstance(clazz));
                    logger.info("Создана фабрика: {}", clazz.getSimpleName());
                }
            }
            initialized = true;
        }
    }


    private static <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance(); // Создаём объект при первом запросе
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании фабрики: " + clazz.getSimpleName(), e);
        }
    }

    public static <T> T getFactory(Class<T> factoryClass) {
        return (T) instances.computeIfAbsent(factoryClass, key -> {
            Supplier<?> supplier = factorySuppliers.get(key);
            if (supplier == null) {
                throw new IllegalArgumentException("Фабрика " + key.getSimpleName() + " не найдена!");
            }
            return supplier.get();
        });
    }
}
