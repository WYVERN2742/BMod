package io.github.wyvern2742.bmod.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;

/**
 * View the status of the server, such as Ticks Per Second, number of players and RAM usage.
 */
public class StatusCommand extends AbstractCommand {

	public StatusCommand(BMod plugin) {
		super(plugin, new String[] { "status", "st"}, Strings.COMMAND_STATUS_SUMMERY,
			Strings.COMMAND_STATUS_DESCRIPTION, Permissions.COMMAND_STATUS);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		return CommandResult.empty();
	}


}
