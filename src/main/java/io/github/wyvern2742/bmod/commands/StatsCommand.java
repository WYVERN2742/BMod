package io.github.wyvern2742.bmod.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;

/**
 * View statistics about the current player, such as mob kills and playtime
 */
public class StatsCommand extends AbstractCommand {

	public StatsCommand(BMod plugin) {
		super(plugin, new String[] { "stats", "s"}, Strings.COMMAND_STATS_SUMMERY,
			Strings.COMMAND_STATS_DESCRIPTION, Permissions.COMMAND_STATS);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		return CommandResult.empty();
	}

}
