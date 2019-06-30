package io.github.wyvern2742.bmod.configuration.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.sql.SqlService;

/**
 * DatabaseManager
 */
public class DatabaseManager {

	/**
	 * Note: official sponge documentation (https://docs.spongepowered.org/stable/en/plugin/database.html)
	 * recommends using {@link SqlService#getConnectionUrlFromAlias(String)}, and configured aliases.
	 * However, this requires the user to setup a database link beforehand.
	 * <p>
	 * Due to the scope of this project, and the requirement of minimum initial configuration
	 * This standard has been ignored, with a hard-coded location provided instead.
	 * This may change in a future update post-release.
	*/
	private static String uri = "jdbc:h2:./mods/bmod/bmod.db";

	private SqlService sql;
	public DataSource getDataSource(String jdbcUrl) throws SQLException {
		if (sql == null) {
			sql = Sponge.getServiceManager().provide(SqlService.class).get();
		}
		return sql.getDataSource(jdbcUrl);
	}

	public String myMethodThatQueries() throws SQLException {
		String sql = "SELECT * FROM Locations";

		try (Connection conn = getDataSource(uri).getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet results = stmt.executeQuery()) {
				results.next();
				return results.getString(1);
		}
	}

	public boolean testDatabase() throws Exception {
		try(Connection conn = getDataSource(uri).getConnection()) {
			// * Sponge does not have any internal methods to convert types into database entries,
			// * Manual deconstruction and construction of types will need to be done.
			// * In future, classes will be created to deal with generic type conversion

			PreparedStatement dropDatanbase = conn.prepareStatement("DROP TABLE IF EXISTS Locations");
			dropDatanbase.execute();

			PreparedStatement locationStmt = conn.prepareStatement("CREATE TABLE Locations(name VARCHAR(30) NOT NULL,x DOUBLE NOT NULL, y DOUBLE NOT NULL, z DOUBLE NOT NULL, rot DOUBLE, PRIMARY KEY(name))");
			locationStmt.execute();

			PreparedStatement testStmt = conn.prepareStatement("INSERT INTO Locations(name, x, y, z, rot) VALUES ('test', 0, 0, 0, 0)");
			testStmt.execute();
			System.out.println("test location created");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
