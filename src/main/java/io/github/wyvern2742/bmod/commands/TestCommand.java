package io.github.wyvern2742.bmod.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;

/**
 * TestCommand
 */
public class TestCommand extends AbstractCommand {

	public TestCommand(BMod plugin) {
		super(plugin, new String[] { "test" }, Strings.COMMAND_TEST_SUMMERY, Strings.COMMAND_TEST_DESCRIPTION, Permissions.COMMAND_TEST);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		return CommandResult.empty();
	}
}
