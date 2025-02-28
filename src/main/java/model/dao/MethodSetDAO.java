package model.dao;

import model.entities.Method;
import model.entities.MethodSet;
import model.entities.Signal;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;



public class MethodSetDAO implements BaseDAO {
    private final Connection connection;

    public MethodSetDAO(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS method_sets (
                id INT PRIMARY KEY AUTO_INCREMENT,
                method_id INT NOT NULL,
                signal_id INT UNIQUE,
                parameters_json TEXT,
                FOREIGN KEY (method_id) REFERENCES methods(id),
                FOREIGN KEY (signal_id) REFERENCES signals(id)
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public MethodSet saveMethodSet(Method method, List<String> parameters) throws SQLException {
        String insertSql = "INSERT INTO method_sets (method_id, parameters_json) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, method.getId());
            // Конвертируем List<String> в JSON
            String jsonParameters = parameters.stream()
                    .map(param -> "\"" + param + "\"")
                    .collect(Collectors.joining(",", "[", "]"));
            ps.setString(2, jsonParameters);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    MethodSet ms = new MethodSet();
                    ms.setId(generatedId);
                    ms.setMethod(method);
                    ms.setParameters(parameters);
                    return ms;
                }
            }
        }
        return null;
    }

    // Если надо привязать сигнал (1:1), можете сделать метод updateSignal(...)
    public void updateSignal(MethodSet ms, Signal signal) throws SQLException {
        String updateSql = "UPDATE method_sets SET signal_id=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
            ps.setLong(1, signal.getId());
            ps.setLong(2, ms.getId());
            ps.executeUpdate();
            ms.setSignal(signal);
        }
    }



}
