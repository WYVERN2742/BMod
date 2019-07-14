package io.github.wyvern2742.bmod.logic;

import java.sql.SQLException;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import io.github.wyvern2742.bmod.configuration.database.DatabaseAdapter;

/**
 * Homes are locations defined within each player's save data, rather than using
 * the Location table such as what {@link Warps} uses
 */
public abstract class Homes {

	public static Optional<Location<World>> getHome(User user, String homeName) throws SQLException {
		return DatabaseAdapter.getPairedLocation(user.getUniqueId().toString(), homeName);
	}

	public static boolean setHome(User user, String name, Location<World> location) throws SQLException {
		return DatabaseAdapter.setPairedLocation(user.getUniqueId().toString(),name, location);
	}
}
