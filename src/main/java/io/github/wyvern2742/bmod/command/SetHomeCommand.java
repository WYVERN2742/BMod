package io.github.wyvern2742.bmod.command;

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
import io.github.wyvern2742.bmod.logic.Homes;

/**
 * Sets the home of the current player.
 * @see HomeCommand
 */
public class SetHomeCommand extends AbstractCommand {

	public SetHomeCommand(BMod plugin) {
		super(plugin, new String[] { "sethome", "homeset","shome","seth" }, Strings.COMMAND_SET_HOME_SUMMERY,
				Strings.COMMAND_SET_HOME_DESCRIPTION, Permissions.COMMAND_SET_HOME);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (!(src instanceof Player)) {
			src.sendMessage(Strings.CONSOLE_EXECUTOR_FAIL);
			return CommandResult.empty();
		}

		Player player = (Player) src;
		try {
			Homes.setHome(player, "home", player.getLocation());
			src.sendMessage(Text.of(Strings.PREFIX, TextColors.GRAY, "Home set to current position"));
			return CommandResult.success();

		} catch (Exception e) {
			// Command failed Unexpectedly
			src.sendMessage(Text.of(Strings.PREFIX, TextColors.RED, "Failed to set home"));
			plugin.logger.error("Failed to set home", e);
		}
		return CommandResult.empty();
	}
}
