package model.dao;

import model.entities.Method;
import model.entities.Ticker;

import java.sql.*;

public class MethodDAO implements BaseDAO {
    private final Connection connection;

    public MethodDAO(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS methods (
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(50) NOT NULL,
                ticker_id INT NOT NULL,
                FOREIGN KEY (ticker_id) REFERENCES tickers(id)
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public Method saveMethod(Ticker ticker, String methodName) throws SQLException {
        String insertSql = "INSERT INTO methods (name, ticker_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, methodName);
            ps.setLong(2, ticker.getId()); // предполагаем, что Ticker уже есть

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    Method m = new Method(methodName);
                    m.setId(generatedId);
                    m.setName(methodName);
                    m.setTicker(ticker);
                    return m;
                }
            }
        }
        return null;
    }


}
