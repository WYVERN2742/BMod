package io.github.wyvern2742.bmod.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;

/**
 * SpawnCommand
 */
public class SpawnCommand extends AbstractCommand {

	public SpawnCommand(BMod plugin) {
		super(plugin, new String[] { "spawn", }, Strings.COMMAND_SPAWN_SUMMERY, Strings.COMMAND_SPAWN_DESCRIPTION,
				Permissions.COMMAND_SPAWN);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		return null;
	}
}
