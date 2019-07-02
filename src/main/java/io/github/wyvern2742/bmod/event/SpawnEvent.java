package io.github.wyvern2742.bmod.event;

import java.util.Optional;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Strings;
import io.github.wyvern2742.bmod.logic.Warps;

/**
 * SpawnEvent
 */
public class SpawnEvent {

	BMod plugin;

	public SpawnEvent(BMod plugin) {
		this.plugin = plugin;
	}

	/**
	 * Handles the chat even by parsing it for blacklisted words, and replacing any
	 * found.
	 *
	 * @param event Chat Message Event
	 */
	@Listener(order = Order.DEFAULT)
	public void onChat(RespawnPlayerEvent event, @First Player player) {
		if (event.isBedSpawn()) {
			// Do not handle respawn if player has a bed
			return;
		}
		try {
			Optional<Location<World>> spawn = Warps.getWarp("spawn");
			if (spawn.isPresent()) {
				// Spawn is set
				event.setToTransform(new Transform<World>(Warps.getWarp("spawn").get()));
			}
			// Spawn is not set, so don't change anything
			return;
		} catch (Exception e) {
			// Something broke
			player.sendMessage(Text.of(Strings.PREFIX, TextColors.RED, "Failed to teleport to spawn"));
			plugin.logger.error("Failed to teleport to spawn", e);
		}
	}
}
