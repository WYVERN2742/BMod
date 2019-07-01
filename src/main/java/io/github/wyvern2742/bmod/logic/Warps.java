package io.github.wyvern2742.bmod.logic;

import java.sql.SQLException;
import java.util.Optional;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import io.github.wyvern2742.bmod.configuration.database.DatabaseAdapter;

/**
 * Warps
 */
public abstract class Warps {

	public static Optional<Location<World>> getWarp(String name) throws SQLException {
		return DatabaseAdapter.getLocation("warp"+name);
	}

	public static boolean setWarp(String name, Location<World> location) throws SQLException {
		return DatabaseAdapter.setLocation("warp"+name, location);
	}
}
