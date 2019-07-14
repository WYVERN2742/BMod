package io.github.wyvern2742.bmod.command;

import java.text.DecimalFormat;
import java.util.Collection;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;
import io.github.wyvern2742.bmod.exception.PlayerNoPermissionException;
import io.github.wyvern2742.bmod.logic.Chunks;

/**
 * View the status of the server, such as Ticks Per Second, number of players
 * and RAM usage.
 */
public class StatusCommand extends AbstractCommand {

	public StatusCommand(BMod plugin) {
		super(plugin, new String[] { "status", "st" }, Strings.COMMAND_STATUS_SUMMERY,
				Strings.COMMAND_STATUS_DESCRIPTION, Permissions.COMMAND_STATUS);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		Collection<Player> players = Sponge.getServer().getOnlinePlayers();
		Builder responseText = Text.builder();
		boolean isPlayer = (src instanceof Player);

		// Players
		responseText.append(ListCommand.getPlayers(src), Text.NEW_LINE);

		// Player ping
		if (players.size() != 0) {
			int average = 0;
			for (Player player : players) {
				average += player.getConnection().getLatency();
			}
			average = average / Sponge.getServer().getOnlinePlayers().size();

			Builder pingText = Text.builder();
			if (isPlayer) {
				// For the player, show their ping with the hover-over showing average
				Builder hoverText = Text.builder();
				hoverText.append(Text.of(TextColors.GRAY, "Average Ping: ", TextColors.GOLD, average, Text.NEW_LINE));

				Player player = (Player) src;
				int playerLatency = player.getConnection().getLatency();

				pingText.append(Text.of(Strings.PREFIX, TextColors.GRAY, "Your Ping: "));

				if (playerLatency < average * 0.9) {
					// Good Ping
					pingText.append(Text.of(TextColors.GREEN, playerLatency, "ms"));
					hoverText.append(Text.of(TextColors.GREEN, "Your ping is better than average"));
				} else if (playerLatency > average * 1.1) {
					// Bad Ping
					pingText.append(Text.of(TextColors.RED, playerLatency, "ms"));
					hoverText.append(Text.of(TextColors.RED, "Your ping is worse than average"));
				} else {
					// Average ping
					pingText.append(Text.of(TextColors.YELLOW, playerLatency, "ms"));
					hoverText.append(Text.of(TextColors.YELLOW, "Your ping is average"));
				}

				pingText.onHover(TextActions.showText(hoverText.build()));
			} else {
				// For the console, show the average ping, assuming 100 is bad and 200 is worse
				pingText.append(Text.of(Strings.PREFIX, TextColors.GRAY, "Average Ping: "));
				if (average < 100) {
					pingText.append(Text.of(TextColors.GREEN, average, "ms"));
				} else if (average < 200) {
					pingText.append(Text.of(TextColors.YELLOW, average, "ms"));
				} else {
					pingText.append(Text.of(TextColors.RED, average, "ms"));
				}
			}
			responseText.append(Text.of(pingText.build(), Text.NEW_LINE));
		}

		// Loaded Chunks
		Builder chunkText = Text.builder();
		chunkText.append(
				Text.of(Strings.PREFIX, TextColors.GRAY, "Loaded Chunks: ", TextColors.GOLD, Chunks.loadedChunks()));
		if (isPlayer) {
			// Add hover for player
			Builder hoverText = Text.builder();
			hoverText.append(Text.of(TextColors.GRAY, "Chunks per player: ", TextColors.GOLD,
					Chunks.loadedChunks() / players.size()));
			chunkText.onHover(TextActions.showText(hoverText.build()));
		} else {
			// Caller is console
			if (players.size() != 0) {
				chunkText.append(Text.of(Text.NEW_LINE, Strings.PREFIX, TextColors.GRAY, "Chunks per player: ",
						TextColors.GOLD, Chunks.loadedChunks() / players.size()));
			}
		}
		responseText.append(Text.of(chunkText.build()));

		// Plugins
		try {
			responseText.append(Text.of(Text.NEW_LINE, PluginCommand.getPlugins(src)));
		} catch (PlayerNoPermissionException e) {
			// No permission to list plugins, fail silently
		}

		// TPS
		TextColor tpsColor;
		Double tps = Sponge.getServer().getTicksPerSecond();
		if (Sponge.getServer().getTicksPerSecond() == 20.0) {
			tpsColor = TextColors.DARK_GREEN;
		} else if (tps > 19) {
			tpsColor = TextColors.GREEN;
		} else if (tps > 18) {
			tpsColor = TextColors.YELLOW;
		} else if (tps > 17) {
			tpsColor = TextColors.GOLD;
		} else if (tps > 15) {
			tpsColor = TextColors.RED;
		} else {
			tpsColor = TextColors.DARK_RED;
		}
		responseText.append(Text.of(Text.NEW_LINE, Strings.PREFIX, TextColors.GRAY, "Ticks Per Second: ", tpsColor,
				new DecimalFormat("0.00").format(tps), Text.NEW_LINE));

		// Ram and free resources

		// Note that freeMemory() is the memory inside the JVM that is ready for new
		// objects
		// In the case of the server, we are only concerned with the amount of memory
		// the process
		// has reserved, and the maximum amount we can reserve.
		Builder memoryText = Text.builder();
		// Maximum amount of memory the server process can use (-Xmx)
		long maxMemory = Runtime.getRuntime().maxMemory();

		// ! This is returning strange values, need to fix and correct to MB
		if (maxMemory == Integer.MAX_VALUE) {
			// No max memory limit
			memoryText.append(Text.of("Using ", TextColors.GOLD, Runtime.getRuntime().totalMemory() / 2048,
					" ", TextColors.GRAY, "of RAM"));
		} else {
			long currentMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
			TextColor memoryColor;
			if (currentMemory > maxMemory * 0.9) {
				memoryColor = TextColors.DARK_RED;
			} else if (currentMemory > maxMemory * 0.8) {
				memoryColor = TextColors.RED;
			} else if (currentMemory > maxMemory * 0.7) {
				memoryColor = TextColors.GOLD;
			} else if (currentMemory > maxMemory * 0.6) {
				memoryColor = TextColors.YELLOW;
			} else if (currentMemory > maxMemory * 0.5) {
				memoryColor = TextColors.GREEN;
			} else {
				memoryColor = TextColors.DARK_GREEN;
			}
			memoryText.append(Text.of(Strings.PREFIX, TextColors.GRAY, "Using ", memoryColor, currentMemory/2048, " / ",
					maxMemory/2048, " (", new DecimalFormat("0.00").format((((double) currentMemory / (double) maxMemory)*100)), "%)",
					TextColors.GRAY, " of RAM"));
		}
		responseText.append(memoryText.build());

		if (!isPlayer) {
			// Pad out with a newline for better display on consoles
			src.sendMessage(Text.of(Text.NEW_LINE, responseText.build()));
		} else {
			src.sendMessage(responseText.build());
		}

		return CommandResult.success();
	}
}
