package io.github.wyvern2742.bmod.command;

import java.util.Map;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.statistic.Statistic;
import org.spongepowered.api.statistic.Statistics;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.format.TextColors;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;

/**
 * View statistics about the current player, such as mob kills and playtime
 */
public class StatsCommand extends AbstractCommand {

	public StatsCommand(BMod plugin) {
		super(plugin, new String[] { "stats", "s", "stat"}, Strings.COMMAND_STATS_SUMMERY,
			Strings.COMMAND_STATS_DESCRIPTION, Permissions.COMMAND_STATS);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		Player player = ((Player) src);

		Map<Statistic, Long> statMap = player.getStatisticData().asMap();
		Builder timePlayed = Text.builder();

		long secondsPlayed = (statMap.get(Statistics.TIME_PLAYED)) / 20;
		int displaySeconds = (int) secondsPlayed % 60;
		long minutesPlayed = secondsPlayed / 60;
		int displayMinutes = (int) minutesPlayed % 60;
		long hoursPlayed = minutesPlayed / 60;

		timePlayed.append(Text.of(Strings.PREFIX, TextColors.GRAY, "Time Played: ", TextColors.GOLD));

		if (hoursPlayed != 0) {
			timePlayed.append(Text.of(hoursPlayed, " hours"));
			if (displayMinutes != 0) {
				if (displaySeconds == 0) {
					timePlayed.append(Text.of(" and "));
				} else {
					timePlayed.append(Text.of(", "));
				}
			}
		}

		if (displayMinutes != 0) {
			timePlayed.append(Text.of(displayMinutes, " minutes"));
			if (displaySeconds != 0) {
				timePlayed.append(Text.of(" and "));
			}
		}
		if (displaySeconds != 0) {
			timePlayed.append(Text.of(displaySeconds, " seconds"));
		}

		src.sendMessage(timePlayed.build());
		return CommandResult.empty();
	}

}
