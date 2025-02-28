package bot.model.dao;

import bot.model.entities.Ticker;

import java.sql.*;

public class TickerDAO implements BaseDAO {
    private final Connection connection;

    public TickerDAO(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS tickers (
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(50) NOT NULL
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    // Сохранение в БД, возвращает объект Ticker с заполненным id
    public Ticker saveTicker(String tickerName) throws SQLException {
        String insertSql = "INSERT INTO tickers (name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, tickerName);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    Ticker ticker = new Ticker(tickerName);
                    ticker.setId(generatedId);
                    ticker.setName(tickerName);
                    return ticker;
                }
            }
        }
        return null; // или бросить исключение, если не удалось
    }



}
