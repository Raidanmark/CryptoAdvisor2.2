package bot.bot.config;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private final Dotenv dotenv;

    //Method to upload .env file
    public Config() {
        dotenv = Dotenv.configure().filename("Secret.env").load();
    }

    //Method to get the token from the .env file
    public String getDiscordToken() {
        return dotenv.get("DISCORD_TOKEN");
    }

    public String getDBRoot() {
        return dotenv.get("DB_ROOT");
    }

    public String getDBUser() {
        return dotenv.get("DB_USER");
    }

    public String getDBPassword() {
        return dotenv.get("DB_PASSWORD");
    }

}
