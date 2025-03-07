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

    public Ticker saveTicker(Ticker ticker) throws SQLException {
        String insertSql = "INSERT INTO tickers (name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, ticker.getName());
            ps.executeUpdate();
        }
        return null; // или бросить исключение, если не удалось
    }

    public Long findIdByName(String name) throws SQLException {
        String selectSql = "SELECT id FROM tickers WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id");
                }
            }
        }
        return null;
    }



}
