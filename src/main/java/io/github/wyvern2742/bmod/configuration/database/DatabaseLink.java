package io.github.wyvern2742.bmod.configuration.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Responds to requests for the execution of specific SQL commands. This class
 * also converts obtained results into an implementation-independent format.
 *
 * @see DatabaseAdapter
 * @see DatabaseManager
 * @version 1.0
 */
public class DatabaseLink {

	private static String SQL_LIST_ALL_LOCATIONS = "SELECT * FROM Locations";
	private static String SQL_GET_LOCATION = "SELECT * FROM Locations WHERE name = ?";
	private static String SQL_NEW_LOCATION = "INSERT INTO Locations(name, x, y, z, world) VALUES (?, ?, ?, ?, ?)";
	private static String SQL_UPDATE_LOCATION = "UPDATE Locations SET name= ?, x=?, y=?, z = ?, world = ? WHERE name = ?";

	/**
	 * Returns an {@link ArrayList} of locations represented via a {@link HashMap}.
	 * The returned locations have values in {@code name}, {@code x}, {@code y},
	 * {@code z}, {@code world} and can be retrieved by using the {@code .get()}
	 * method.
	 *
	 * <pre>
	 * map.get("name"); // Name of the location
	 * map.get("x"); // X-position of the location
	 * map.get("y"); // Y-position of the location
	 * map.get("z"); // Z-position of the location
	 * map.get("world"); // Name of the location's world
	 * </pre>
	 *
	 * @return {@link ArrayList} of locations, each represented by a {@link HashMap}
	 * @throws SQLException Thrown when any error occurs with the database
	 */
	public ArrayList<HashMap<String, String>> listAllLocations() throws SQLException {
		ArrayList<HashMap<String, String>> locationList = new ArrayList<HashMap<String, String>>();

		try (Connection conn = DatabaseManager.getInstance().getConnection(); Statement st = conn.createStatement()) {
			ResultSet rs = st.executeQuery(SQL_LIST_ALL_LOCATIONS);

			while (rs.next()) {
				HashMap<String, String> location = new HashMap<String, String>();

				location.put("name", rs.getString("name"));
				location.put("x", rs.getString("x"));
				location.put("y", rs.getString("y"));
				location.put("z", rs.getString("z"));
				location.put("world", rs.getString("world"));

				locationList.add(location);
			}
		}
		return locationList;
	}

	/**
	 * Returns a single location represented via a {@link HashMap}. The returned
	 * location has values in {@code name}, {@code x}, {@code y}, {@code z},
	 * {@code world} and these can be retrieved by using the {@code .get()} method.
	 * <p>
	 * If no location is found by the given name, an empty hashmap is returned.
	 *
	 * <pre>
	 * map.get("name"); // Name of the location
	 * map.get("x"); // X-position of the location
	 * map.get("y"); // Y-position of the location
	 * map.get("z"); // Z-position of the location
	 * map.get("world"); // Name of the location's world
	 * </pre>
	 *
	 * @return Location represented as a {@link HashMap}
	 * @param name Name of the location to return
	 * @throws SQLException Thrown when any error occurs with the database
	 */
	public HashMap<String, String> getLocation(String name) throws SQLException {
		HashMap<String, String> location = new HashMap<String, String>();
		try (Connection conn = DatabaseManager.getInstance().getConnection();
				PreparedStatement st = conn.prepareStatement(SQL_GET_LOCATION)) {
			st.setString(1, name);
			ResultSet rs = st.executeQuery();

			if (!rs.next()) {
				return location;
			}

			location.put("name", rs.getString("name"));
			location.put("x", rs.getString("x"));
			location.put("y", rs.getString("y"));
			location.put("z", rs.getString("z"));
			location.put("world", rs.getString("world"));

		}
		return location;
	}

	/**
	 * Returns true if a location with the provided name exists
	 *
	 * @return True if location exists
	 * @param name Name of the location to check
	 * @throws SQLException Thrown when any error occurs with the database
	 */
	public boolean locationExists(String name) throws SQLException {
		try (Connection conn = DatabaseManager.getInstance().getConnection();
				PreparedStatement st = conn.prepareStatement(SQL_GET_LOCATION)) {
			st.setString(1, name);
			ResultSet rs = st.executeQuery();
			return rs.next();
		}
	}

	/**
	 * Creates a new location with the given name and location data. No checks are
	 * performed on the input and instead any {@link SQLExceptions} are thrown up
	 * the chain.
	 *
	 * @param name  Unique Name of the Location
	 * @param x     X-Position of the Location
	 * @param y     Y-Position of the Location
	 * @param z     Z-Position of the Location
	 * @param world Location's World name
	 * @return Integer of rows updated.
	 * @throws SQLException Thrown when any error executing the update function
	 *                      arises, this may be related to the database connection,
	 *                      or invalid arguments.
	 */
	public int newLocation(String name, Double x, Double y, Double z, String world) throws SQLException {
		try (Connection conn = DatabaseManager.getInstance().getConnection();
				PreparedStatement st = conn.prepareStatement(SQL_NEW_LOCATION)) {
				st.setString(1, name);
				st.setDouble(2, x);
				st.setDouble(3, y);
				st.setDouble(4, z);
				st.setString(5, world);

			return st.executeUpdate();
		}
	}

	/**
	 * Updates an existing location with the given name and location data. No checks are
	 * performed on the input and instead any {@link SQLExceptions} are thrown up
	 * the chain.
	 *
	 * @param name  Unique Name of the Location
	 * @param x     X-Position of the Location
	 * @param y     Y-Position of the Location
	 * @param z     Z-Position of the Location
	 * @param world Location's World name
	 * @return Integer of rows updated.
	 * @throws SQLException Thrown when any error executing the update function
	 *                      arises, this may be related to the database connection,
	 *                      or invalid arguments.
	 * @see DatabaseLink#newLocation(String, String, String, String, String)
	 */
	public int updateLocation(String name, Double x, Double y, Double z, String world) throws SQLException {
		try (Connection conn = DatabaseManager.getInstance().getConnection();
				PreparedStatement st = conn.prepareStatement(SQL_UPDATE_LOCATION)) {
			st.setString(1, name);
			st.setDouble(2, x);
			st.setDouble(3, y);
			st.setDouble(4, z);
			st.setString(5, world);
			st.setString(6, name);

			return st.executeUpdate();
		}
	}
}
