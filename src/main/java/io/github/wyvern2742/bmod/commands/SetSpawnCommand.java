package io.github.wyvern2742.bmod.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;

/**
 * SetSpawnCommand
 */
public class SetSpawnCommand extends AbstractCommand {

	public SetSpawnCommand(BMod plugin) {
		super(plugin, new String[] { "setspawn", "spawnset", "sspawn" }, Strings.COMMAND_SPAWN_SET_SUMMERY,
				Strings.COMMAND_SPAWN_SET_DESCRIPTION, Permissions.COMMAND_SPAWN_SET);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		return CommandResult.empty();
	}

}
