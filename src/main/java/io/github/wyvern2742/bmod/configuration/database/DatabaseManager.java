package io.github.wyvern2742.bmod.configuration.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.sql.SqlService;

/**
 * Manages and controls the database, such as connections, uris, etc. Also
 * provides functions for creation and existence checks.
 *
 * @version 1.0
 * @see DatabaseLink
 * @see DatabaseAdapter
 */
public class DatabaseManager {

	/**
	 * Note: official sponge documentation
	 * (https://docs.spongepowered.org/stable/en/plugin/database.html) recommends
	 * using {@link SqlService#getConnectionUrlFromAlias(String)}, and configured
	 * aliases. However, this requires the user to setup a database link beforehand.
	 * <p>
	 * Due to the scope of this project, and the requirement of minimum initial
	 * configuration This standard has been ignored, with a hard-coded location
	 * provided instead. This may change in a future update post-release.
	 */
	private static DataSource ds;

	private static SqlService sql;
	private static DatabaseManager instance;

	/**
	 * Use {@link DatabaseManager#getInstance()}
	 *
	 * @throws SQLException
	 */
	private DatabaseManager() throws SQLException {
		ds = getDataSource("jdbc:h2:./mods/bmod/bmod.db");
	}

	/**
	 * Get the Singleton instance of the DatabaseManager
	 *
	 * @return Shared Instance of the DatabaseManager
	 * @throws SQLException Thrown when any issue is encountered creating the
	 *                      instance
	 */
	public static DatabaseManager getInstance() throws SQLException {
		if (instance == null) {
			instance = new DatabaseManager();
		}
		return instance;
	}

	/**
	 * Obtain the data source from the provided jdbc url. Also sets the
	 * {@link DatabaseManager#sql} service to one provided by Sponge's Service
	 * Manager.
	 *
	 * @param jdbcUrl database connection url
	 * @return Returns a {@link DataSource} linked to the database
	 * @throws SQLException Raised if any issue is encountered getting the data
	 *                      source
	 */
	private static DataSource getDataSource(String jdbcUrl) throws SQLException {
		if (sql == null) {
			sql = Sponge.getServiceManager().provide(SqlService.class).get();
		}
		return sql.getDataSource(jdbcUrl);
	}

	/**
	 * Returns a database connection.
	 * <p>
	 * <b>Remember to close the connection</b>
	 *
	 * @return Database Connection
	 * @throws SQLException Thrown if a database access error occurs
	 */
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	/**
	 * Performs a simple query against the database to determine if the database is
	 * constructed.
	 * <p>
	 * It should be noted that this function does not check the schema and will not
	 * notify if some tables are missing.
	 *
	 * @return True if database exists
	 */
	public boolean databaseExists() {
		String sql = "SELECT * FROM Locations LIMIT 0, 1";
		try (Connection conn = ds.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			// We do not need the results
			stmt.execute();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Drops any existing database and re-generates.
	 * <p>
	 * ! <b>Using this will delete all existing database data</b>
	 *
	 * @throws SQLException Thrown if any exceptions are raised during database
	 *                      creation
	 */
	public void constructDatabase() throws SQLException {
		try (Connection conn = ds.getConnection()) {
			// Drop database
			PreparedStatement cleanDatabase = conn.prepareStatement("DROP TABLE IF EXISTS Locations");
			cleanDatabase.execute();

			// Create Location table
			PreparedStatement locationStmt = conn.prepareStatement(
					"CREATE TABLE Locations(name VARCHAR(32) NOT NULL, x DOUBLE NOT NULL, y DOUBLE NOT NULL, z DOUBLE NOT NULL, world VARCHAR(32), PRIMARY KEY(name))");
			locationStmt.execute();
		}
	}
}
