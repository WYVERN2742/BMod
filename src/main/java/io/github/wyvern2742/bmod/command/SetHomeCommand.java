package io.github.wyvern2742.bmod.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;

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
		return CommandResult.empty();
	}
}
