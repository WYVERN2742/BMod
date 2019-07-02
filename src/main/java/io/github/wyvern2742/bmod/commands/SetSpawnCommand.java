package io.github.wyvern2742.bmod.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;
import io.github.wyvern2742.bmod.logic.Warps;

/**
 * Set the spawn of the server. All players who join the server will start at
 * this location.
 *
 * @see SpawnCommand
 */
public class SetSpawnCommand extends AbstractCommand {

	public SetSpawnCommand(BMod plugin) {
		super(plugin, new String[] { "setspawn", "spawnset", "sspawn" }, Strings.COMMAND_SPAWN_SET_SUMMERY,
				Strings.COMMAND_SPAWN_SET_DESCRIPTION, Permissions.COMMAND_SPAWN_SET);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (src instanceof Player) {
			Player player = (Player) src;
			try {
				Warps.setWarp("spawn", player.getLocation());
				src.sendMessage(Text.of(Strings.PREFIX, TextColors.GRAY, "Spawn set to current position"));
			} catch (Exception e) {
				src.sendMessage(Text.of(Strings.PREFIX, TextColors.RED, "Failed to create spawn point"));
				plugin.logger.error("Failed to set spawn point", e);
			}
		}
		return CommandResult.empty();
	}
}
