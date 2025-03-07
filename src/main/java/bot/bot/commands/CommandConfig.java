package bot.bot.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class CommandConfig {
    private static CommandConfig instance;
    public Map<String, CommandEntry> commands;

    public static CommandConfig getInstance() {
        if (instance == null) {
            instance = loadConfig("commands.yml");
        }
        return instance;
    }

    private static CommandConfig loadConfig(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            // Загружаем из classpath (правильный путь для JAR)
            InputStream inputStream = CommandConfig.class.getClassLoader().getResourceAsStream("commands.yml");
            if (inputStream != null) {
                return mapper.readValue(inputStream, CommandConfig.class);
            }

            // Если не нашли в classpath, пробуем загрузить из внешнего файла
            File file = new File(filePath);
            if (file.exists()) {
                return mapper.readValue(file, CommandConfig.class);
            }

            throw new RuntimeException("Файл " + filePath + " не найден!");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки YAML-файла команд", e);
        }
    }

    public static class CommandEntry {
        public String name;
        public String message;
        public String available_in_status;
        public String new_status;
        public Messages messages;

        public static class Messages {
            public String inactive;
            public String active;
        }
    }
}
