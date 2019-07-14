package io.github.wyvern2742.bmod.configuration.database;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

import com.flowpowered.math.vector.Vector3d;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * Converts the values obtained from {@link DatabaseLink} into Sponge classes.
 * It is recommended to use this class over the {@link DatabaseLink} class
 * directly.
 *
 * @see DatabaseLink
 */
public class DatabaseAdapter {

	/**
	 * Returns an {@link HashMap} of all {@link Location} instances in the database
	 * These can be accessed by using their name as the key, or by using the inbuilt
	 * HashMap iterator methods.
	 * <p>
	 * If no Locations are set, the returned hashmap will be empty.
	 *
	 * @return {@link HashMap} of {@link Location}s, with their name as key
	 * @throws SQLException
	 */
	public static HashMap<String, Location<World>> getAllLocations() throws SQLException {
		DatabaseLink link = new DatabaseLink();
		HashMap<String, Location<World>> locations = new HashMap<String, Location<World>>();

		for (HashMap<String, String> location : link.listAllLocations()) {
			// Loop each hashmap location, and create a Location
			Optional<Location<World>> world = createLocation(location);

			if (world.isPresent()) {
				locations.put(location.get("name"), world.get());
			}
		}
		return locations;
	}

	/**
	 * Returns a location with the provided name.
	 * <p>
	 * If the location with the provided name does not exist,
	 * {@link Optional#isPresent()} will return false
	 *
	 * @param name Name of the location to obtain
	 * @return {@link Location} with the provided name
	 * @throws SQLException Thrown if any error is encountered reading the location
	 *                      from the database
	 */
	public static Optional<Location<World>> getLocation(String name) throws SQLException {
		DatabaseLink link = new DatabaseLink();

		HashMap<String, String> location = link.getLocation(name);
		if (location.size() == 0) {
			// Hashmap is empty, no locations are defined
			return Optional.empty();
		}
		return createLocation(location);
	}

	/**
	 * Save the provided location to the database, overwriting any location with the
	 * name set already.
	 *
	 * @param name     Name of the location to save
	 * @param location Location to save
	 * @return Returns True if the location was saved successfully, false otherwise
	 * @throws SQLException Thrown if any issues occurred when updating the database
	 */
	public static boolean setLocation(String name, Location<World> location) throws SQLException {
		DatabaseLink link = new DatabaseLink();
		int updated = 0;
		if (link.locationExists(name)) {
			updated = link.updateLocation(name, location.getPosition().getX(), location.getPosition().getY(),
					location.getPosition().getZ(), location.getExtent().getName());
		} else {
			updated = link.newLocation(name, location.getPosition().getX(), location.getPosition().getY(),
					location.getPosition().getZ(), location.getExtent().getName());
		}
		return !(updated == 0);
	}

	/**
	 * Creates a {@link Location} from the provided
	 * {@link DatabaseLink#getLocation(String)} format {@link HashMap}
	 *
	 * @param locationMap A Hashmap with values on {@code x}, {@code y}, {@code z},
	 *                    and {@code world}
	 * @return {@link Optional} populated with the transformed location. The
	 *         Optional will be empty if any issues occurred during transformation.
	 */
	public static Optional<Location<World>> createLocation(HashMap<String, String> locationMap) {
		try {
			return createLocation(Double.parseDouble(locationMap.get("x")), Double.parseDouble(locationMap.get("y")),
					Double.parseDouble(locationMap.get("z")), locationMap.get("world"));
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	/**
	 * Create a location from the given parameters.
	 * <p>
	 * If the worldName provided does not exist, an attempt is made to use the
	 * default sponge world instead, However, if this is not possible, an empty
	 * {@link Optional} is returned instead of a generated location.
	 *
	 * @param x         X-Position of the location
	 * @param y         Y-Position of the location
	 * @param z         Z-Position of the location
	 * @param worldName Name of the world the location is in
	 * @return {@link Optional} populated with the transformed location. The
	 *         Optional will be empty if no world could be transformed.
	 */
	public static Optional<Location<World>> createLocation(Double x, Double y, Double z, String worldName) {
		Vector3d pos = new Vector3d(x, y, z);

		Optional<World> worldOptional = Sponge.getServer().getWorld(worldName);

		World world = null;

		if (worldOptional.isPresent()) {
			// World defined in the location is present
			world = worldOptional.get();
		} else {
			// Defined world is not present, try to get default
			if (Sponge.getServer().getWorld(Sponge.getServer().getDefaultWorldName()).isPresent()) {
				// Default world is preset, change world to default
				world = Sponge.getServer().getWorld(Sponge.getServer().getDefaultWorldName()).get();
			} else {
				// Uhh, somehow the default world doesn't exist?
				return Optional.empty();
			}
		}
		return Optional.of(new Location<World>(world, pos));
	}


	/**
	 * Returns an {@link HashMap} of all {@link Location} instances related to the provided UUID
	 * in the database. These can be accessed by using their name as the key, or by using the inbuilt
	 * HashMap iterator methods.
	 * <p>
	 * If no PairedLocations are set, the returned hashmap will be empty.
	 *
	 * @param uuid Unique identifier linked to the location
	 * @return {@link HashMap} of {@link Location}s, with their name as key
	 * @throws SQLException
	 */
	public static HashMap<String, Location<World>> getPairedLocations(String uuid) throws SQLException {
		DatabaseLink link = new DatabaseLink();
		HashMap<String, Location<World>> locations = new HashMap<String, Location<World>>();

		for (HashMap<String, String> location : link.listPairedLocations(uuid)) {
			// Loop each hashmap location, and create a Location
			Optional<Location<World>> world = createLocation(location);

			if (world.isPresent()) {
				locations.put(location.get("name"), world.get());
			}
		}
		return locations;
	}

	/**
	 * Returns a location with a given name related to the provided UUID.
	 * <p>
	 * If the location with the provided name does not exist,
	 * {@link Optional#isPresent()} will return false
	 *
	 * @param uuid Unique Identifier linked to the location
	 * @param name Name of the location to obtain
	 * @return {@link Location} with the provided name
	 * @throws SQLException Thrown if any error is encountered reading the location
	 *                      from the database
	 */
	public static Optional<Location<World>> getPairedLocation(String uuid, String name) throws SQLException {
		DatabaseLink link = new DatabaseLink();

		HashMap<String, String> location = link.getPairedLocation(uuid, name);
		if (location.size() == 0) {
			// Hashmap is empty, no locations are defined
			return Optional.empty();
		}
		return createLocation(location);
	}

	/**
	 * Save the provided location to the database related to the UUID, overwriting any paired location with the
	 * name already.
	 *
	 * @param uuid     Unique identifier linked to the location
	 * @param name     Name of the location to save
	 * @param location Location to save
	 * @return Returns True if the location was saved successfully, false otherwise
	 * @throws SQLException Thrown if any issues occurred when updating the database
	 */
	public static boolean setPairedLocation(String uuid, String name, Location<World> location) throws SQLException {
		DatabaseLink link = new DatabaseLink();
		int updated = 0;
		if (link.pairedLocationExists(uuid, name)) {
			updated = link.updatePairedLocation(uuid, name, location.getPosition().getX(), location.getPosition().getY(),
					location.getPosition().getZ(), location.getExtent().getName());
		} else {
			updated = link.newPairedLocation(uuid, name, location.getPosition().getX(), location.getPosition().getY(),
					location.getPosition().getZ(), location.getExtent().getName());
		}
		return !(updated == 0);
	}

}
