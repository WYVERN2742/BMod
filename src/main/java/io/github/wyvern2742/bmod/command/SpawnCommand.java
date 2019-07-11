package io.github.wyvern2742.bmod.command;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;
import io.github.wyvern2742.bmod.logic.Warps;

/**
 * Teleports the player to the set spawn
 *
 * @see SetSpawnCommand
 */
public class SpawnCommand extends AbstractCommand {

	public SpawnCommand(BMod plugin) {
		super(plugin, new String[] { "spawn", }, Strings.COMMAND_SPAWN_SUMMERY, Strings.COMMAND_SPAWN_DESCRIPTION,
				Permissions.COMMAND_SPAWN);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (src instanceof Player) {
			Player player = (Player) src;
			try {
				Optional<Location<World>> spawn = Warps.getWarp("spawn");
				if (spawn.isPresent()) {
					player.setLocation(spawn.get());
					src.sendMessage(Text.of(Strings.PREFIX, TextColors.GRAY,"Teleported to spawn"));
					return CommandResult.success();
				} else {
					src.sendMessage(Text.of(Strings.PREFIX, TextColors.GRAY, "Spawn is not set"));
				}
			} catch (Exception e) {
				src.sendMessage(Text.of(Strings.PREFIX, TextColors.RED, "Failed to teleport to spawn"));
				plugin.logger.error("Failed to teleport to spawn", e);
			}
		}
		return CommandResult.empty();
	}
}
