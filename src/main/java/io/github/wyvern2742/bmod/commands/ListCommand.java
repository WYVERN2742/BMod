package io.github.wyvern2742.bmod.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;

/**
 * ListCommand
 */
public class ListCommand extends AbstractCommand {

	public ListCommand(BMod plugin) {
		super(plugin, new String[] { "list", "ls"}, Strings.COMMAND_LIST_SUMMERY,
			Strings.COMMAND_LIST_DESCRIPTION, Permissions.COMMAND_LIST);
	}
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		return CommandResult.empty();
	}
}
