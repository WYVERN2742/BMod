package io.github.wyvern2742.bmod.command;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;
import io.github.wyvern2742.bmod.logic.Homes;

/**
 * Teleports the user to their set home
 * @see SetHomeCommand
 */
public class HomeCommand extends AbstractCommand {

	public HomeCommand(BMod plugin) {
		super(plugin, new String[] { "home" }, Strings.COMMAND_HOME_SUMMERY, Strings.COMMAND_HOME_DESCRIPTION,
				Permissions.COMMAND_HOME);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (!(src instanceof Player)) {
			src.sendMessage(Strings.CONSOLE_EXECUTOR_FAIL);
			return CommandResult.empty();
		}

		Player player = (Player) src;
		try {
			Optional<Location<World>> home = Homes.getHome((User)player, "home");
			if (home.isPresent()) {
				player.setLocation(home.get());
				src.sendMessage(Text.of(Strings.PREFIX, TextColors.GRAY,"Teleported to home"));
				return CommandResult.success();
			} else {
				src.sendMessage(Text.of(Strings.PREFIX, TextColors.GRAY, "Home is not set"));
			}
		} catch (Exception e) {
			// Command failed unexpectedly
			src.sendMessage(Text.of(Strings.PREFIX, TextColors.RED, "Failed to teleport home"));
			plugin.logger.error("Failed to teleport home", e);
		}
		return CommandResult.empty();
	}
}
