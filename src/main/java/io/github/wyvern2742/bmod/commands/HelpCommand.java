package io.github.wyvern2742.bmod.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;

/**
 * The help command prints out the list of registered commands by BMod and displays them to the user.
 */
public class HelpCommand extends AbstractCommand {

	public HelpCommand(BMod plugin) {
		super(plugin, new String[] {"h"}, Strings.COMMAND_HELP_SUMMERY, Strings.COMMAND_HELP_DESCRIPTION,
				Permissions.COMMAND_HELP);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		AbstractCommand[] commands = plugin.getCommands();

		for (AbstractCommand command : commands) {
			Builder textContent = Text.builder();

			// Create a list of aliases (Alternative commands)
			String[] commandAliases = new String[command.getAliases().length - 1];
			for (int i = 1; i < command.getAliases().length; i++) {
				commandAliases[i - 1] = command.getAliases()[i];
			}

			// Add prefix and command
			textContent.append(Strings.PREFIX);
			textContent.append(Text.of(TextColors.GOLD, "/", command.getAliases()[0]," "));

			// Add help summery w/ hover-over full description for players, otherwise just
			// the full description for other callers.
			Builder summeryText = Text.builder();
			if (src instanceof Player) {
				// Caller is a player
				summeryText.append(Text.of(TextColors.GRAY, command.getSummery()));
			} else {
				// Caller is not a player, and cannot use .onHover() actions
				summeryText.append(command.getDescription());
			}

			textContent.append(summeryText.build());

			if (command.getAliases().length != 1) {
				// Has an alias, add Alias list.
				Builder aliasText = Text.builder();
				if (command.getAliases().length > 2) {
					// More than two aliases
					// Remember, the 1st "alias" is treated as the command

					aliasText.append(Text.of(TextColors.YELLOW, " Aliases: "));



					// Work out if the line will overflow when the full alias list is printed
					boolean overflows = Strings.overflowsLine(textContent.build().toPlain().length(),
						aliasText.build().toPlain().length() + String.join(" /", commandAliases).length());

					if (overflows && src instanceof Player) {
						// Alias list needs to be compacted to stay on one line if the user is a player.
						// Do this by showing the aliases as a number, and hide the list in an
						// hover-over

						aliasText.append(Text.of(TextColors.YELLOW, "+" + commandAliases.length));
					} else {
						// Alias list will not overflow, therefore add to line
						aliasText.append(Text.of(TextColors.YELLOW,"/", String.join(" /", commandAliases)));
					}

				} else {
					// Only one alias, append alias to help even if it overflows
					aliasText.append(Text.of(TextColors.YELLOW, " Alias: /", command.getAliases()[1]));
				}

				// Alias text is constructed, add it to the help text
				textContent.append(aliasText.build());

			}

			// If the caller is a player, create detailed hover-over info, and bind to suggest the
			// command when clicked
			if (src instanceof Player) {
				Builder hoverText = Text.builder();

				hoverText.append(Text.of(TextColors.GOLD, "/", command.getAliases()[0], Text.NEW_LINE,
				TextColors.WHITE, command.getSummery(), Text.NEW_LINE,
				TextColors.GRAY, command.getDescription(), Text.NEW_LINE));

				if (commandAliases.length > 0) {
					hoverText.append(Text.of(TextColors.YELLOW, "Aliases: /", String.join(" /", commandAliases),Text.NEW_LINE));
				}

				hoverText.append(Text.of(Text.NEW_LINE,TextColors.DARK_GRAY, "Click to suggest this command"));
				textContent.onHover(TextActions.showText(hoverText.build()));

				textContent.onClick(TextActions.suggestCommand("/" + command.getAliases()[0]));
				// * Note: shift-click can only insert text, therefore we default to suggesting on click
				// * rather than providing the user a way to run on click for useability purposes.
			}

			// Help text is finished.
			src.sendMessage(textContent.build());
		}
		return CommandResult.success();

	}
}
