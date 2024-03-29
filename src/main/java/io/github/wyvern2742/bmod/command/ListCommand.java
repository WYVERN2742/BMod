package io.github.wyvern2742.bmod.command;

import org.spongepowered.api.Sponge;
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
 * Displays a list of users currently playing on the server
 */
public class ListCommand extends AbstractCommand {

	public ListCommand(BMod plugin) {
		super(plugin, new String[] { "list", "ls" }, Strings.COMMAND_LIST_SUMMERY, Strings.COMMAND_LIST_DESCRIPTION,
				Permissions.COMMAND_LIST);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		src.sendMessage(getPlayers(src, !(src instanceof Player)));
		return CommandResult.success();
	}

	public static Text getPlayers(CommandSource src) {
		return getPlayers(src, false);
	}

	public static Text getPlayers(CommandSource src, boolean longForm) {
		boolean supportsHover = src instanceof Player;

		Player[] players = Sponge.getServer().getOnlinePlayers()
				.toArray(new Player[Sponge.getServer().getOnlinePlayers().size()]);

		if (players.length == 0) {
			// No players online
			return Text.of(Strings.PREFIX, TextColors.GRAY, "There are no players online");
		}

		if (longForm) {
			// Return longForm output
			return buildLongForm(players);
		}

		// Construct chat text

		Builder textBuilder = Text.builder();
		if (players.length == 1) {
			// English single
			textBuilder.append(Text.of(Strings.PREFIX, TextColors.GRAY, "There is ", TextColors.GOLD, players.length,
					TextColors.GRAY, " player online"));
		} else {
			// English Plural
			textBuilder.append(Text.of(Strings.PREFIX, TextColors.GRAY, "There are ", TextColors.GOLD, players.length,
					TextColors.GRAY, " players online"));
		}

		if (!supportsHover) {
			return textBuilder.build();
		}

		textBuilder.onHover(TextActions.showText(buildLongForm(players)));
		return textBuilder.build();
	}

	private static Text buildLongForm(Player[] players) {

		if (players.length == 0) {
			return Text.EMPTY;
		}

		Builder longFormText = Text.builder();
		longFormText.append();

		// Construct list of player names
		for (int i = 0; i < players.length; i++) {
			Player player = players[i];

			if (player.getDisplayNameData().displayName().exists()) {
				longFormText.append(Text.of(TextColors.GRAY, player.getDisplayNameData().displayName().get()));
			} else {
				longFormText.append(Text.of(TextColors.GRAY, player.getName()));
			}
			if (i + 1 != players.length) {
				longFormText.append(Text.of(TextColors.GRAY, ", "));
			}
		}
		return longFormText.build();
	}
}
