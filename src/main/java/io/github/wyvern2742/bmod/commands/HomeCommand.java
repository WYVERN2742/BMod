package io.github.wyvern2742.bmod.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;

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
		return CommandResult.empty();
	}
}
