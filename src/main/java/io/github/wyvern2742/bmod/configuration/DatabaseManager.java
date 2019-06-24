package io.github.wyvern2742.bmod.configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.sql.SqlService;
import org.spongepowered.api.text.Text;

/**
 * DatabaseManager
 */
public class DatabaseManager {

	private SqlService sql;
	public DataSource getDataSource(String jdbcUrl) throws SQLException {
		if (sql == null) {
			sql = Sponge.getServiceManager().provide(SqlService.class).get();
		}
		return sql.getDataSource(jdbcUrl);
	}

	public void myMethodThatQueries() throws SQLException {
		String uri = "jdbc:h2:bmod.db";
		String sql = "SELECT * FROM test_tbl";

		try (Connection conn = getDataSource(uri).getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet results = stmt.executeQuery()) {

			while (results.next()) {
				Sponge.getServer().getConsole().sendMessage(Text.of(results.getString(1)));
			}

		}

	}
}
