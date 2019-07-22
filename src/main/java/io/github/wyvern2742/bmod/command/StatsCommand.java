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
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;

/**
 * View statistics about the current player, such as mob kills and playtime
 */
public class StatsCommand extends AbstractCommand {

	public StatsCommand(BMod plugin) {
		super(plugin, new String[] { "stats", "s", "stat" }, Strings.COMMAND_STATS_SUMMERY,
				Strings.COMMAND_STATS_DESCRIPTION, Permissions.COMMAND_STATS);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (!(src instanceof Player)) {
			src.sendMessage(Strings.CONSOLE_EXECUTOR_FAIL);
			return CommandResult.empty();
		}

		Player player = ((Player) src);
		Map<Statistic, Long> statMap = player.getStatisticData().asMap();
		Builder statText = Text.builder();
		// Statistics.TIME_SINCE_DEATH;
		// Statistics.CAKE_SLICES_EATEN;

		// Time Played
		Builder timePlayed = Text.builder();
		Builder timePlayedHover = Text.builder();

		// If a statistic is not set, the get returns null, therefore add null checks to
		// retrieved statistics.
		long timesDisconnected = getStat(Statistics.LEAVE_GAME, statMap);
		long secondsPlayed = getStat(Statistics.TIME_PLAYED, statMap) / 60;

		timePlayed.append(Text.of(Strings.PREFIX, TextColors.GRAY, "Playtime: ", TextColors.GOLD,
				buildTime(secondsPlayed, false)));

		if (timesDisconnected == 0) {
			timePlayedHover.append(Text.of(TextColors.GREEN, "First time on this server! Welcome them!"));
		} else {
			timePlayedHover.append(Text.of(TextColors.GRAY, "Average Session: ", TextColors.GOLD,
					buildTime(secondsPlayed / timesDisconnected, false)));
			timePlayedHover.append(Text.of(Text.NEW_LINE, TextColors.GRAY, "Times Disconnected: ", TextColors.GOLD,
					timesDisconnected));
		}

		timePlayed.onHover(TextActions.showText(timePlayedHover.build()));
		statText.append(timePlayed.build());

		// Kills and Deaths
		Builder killsText = Text.builder();
		Builder killsTextHover = Text.builder();

		long damageDealt = getStat(Statistics.DAMAGE_DEALT, statMap);
		long damageTaken = getStat(Statistics.DAMAGE_TAKEN, statMap);
		long deaths = getStat(Statistics.DEATHS, statMap);
		long lastDeath = getStat(Statistics.TIME_SINCE_DEATH, statMap) / 60;

		String mobs = getStat(Statistics.MOB_KILLS, statMap)==1 ? " mob" : " mobs";
		String players = getStat(Statistics.PLAYER_KILLS, statMap)==1 ? " player" : " players";


		killsText.append(Text.of(Strings.PREFIX, TextColors.GRAY, "Killed ", TextColors.GREEN,
				getStat(Statistics.MOB_KILLS, statMap), TextColors.GRAY, mobs, " and ", TextColors.AQUA,
				getStat(Statistics.PLAYER_KILLS, statMap), TextColors.GRAY, players));

		killsTextHover.append(Text.of(TextColors.GRAY, "Total Damage: ", TextColors.GREEN, damageDealt, Text.NEW_LINE,
				TextColors.GRAY, "Damage Received: ", TextColors.AQUA, damageTaken, Text.NEW_LINE));
		if (deaths == 0) {
			killsTextHover.append(Text.of(TextColors.GREEN, "No deaths"));
		} else {
			killsTextHover
					.append(Text.of(TextColors.GRAY, "Deaths: ", TextColors.RED, deaths, Text.NEW_LINE, TextColors.GRAY,
							"Last died ", TextColors.GOLD, buildTime(lastDeath, true), TextColors.GRAY, " ago"));
		}

		killsText.onHover(TextActions.showText(killsTextHover.build()));
		statText.append(Text.of(Text.NEW_LINE, killsText.build()));

		// Distance traveled
		Builder distanceText = Text.builder();
		Builder distanceTextHover = Text.builder();

		// Whew, quite a bunch of different stats here
		long distanceWalked = getStat(Statistics.WALK_ONE_CM, statMap) / 100;
		long distanceSprinted = getStat(Statistics.SPRINT_ONE_CM, statMap) / 100;
		long distanceCrouched = getStat(Statistics.CROUCH_ONE_CM, statMap) / 100;
		long distanceBoated = getStat(Statistics.BOAT_ONE_CM, statMap) / 100;
		long distanceFallen = getStat(Statistics.FALL_ONE_CM, statMap) / 100;
		long distanceRode = (getStat(Statistics.HORSE_ONE_CM, statMap) + getStat(Statistics.PIG_ONE_CM, statMap)) / 100;
		long distanceFlown = getStat(Statistics.AVIATE_ONE_CM, statMap) / 100;
		long distanceSwum = getStat(Statistics.SWIM_ONE_CM, statMap) / 100;
		long distanceCarted = getStat(Statistics.MINECART_ONE_CM, statMap) / 100;
		long distanceDived = getStat(Statistics.DIVE_ONE_CM, statMap) / 100;

		// (Not currently displayed to user)
		long distanceLand = distanceWalked + distanceSprinted + distanceCrouched;
		long distanceAir = distanceFallen + distanceFlown;
		long distanceWater = distanceSwum + distanceDived;
		long distanceRidden = distanceCarted + distanceRode + distanceBoated;

		// 'Total' distance travelled
		// Note that some movement may be recorded within statistics but not displayed

		long distanceMoved = distanceLand + distanceAir + distanceWater + distanceRidden;

		distanceText.append(Text.of(Strings.PREFIX, TextColors.GRAY, "Travelled ", TextColors.GOLD, distanceMoved,
				TextColors.GRAY, "m"));

		distanceTextHover.append(Text.of(TextColors.GRAY, "Walked: ", TextColors.GOLD, distanceWalked, TextColors.GRAY,
				"m Sprant: ", TextColors.GOLD, distanceSprinted, TextColors.GRAY, "m Snuck: ", TextColors.GOLD,
				distanceCrouched, TextColors.GRAY, "m", Text.NEW_LINE, "Fallen: ", TextColors.GOLD, distanceFallen,
				TextColors.GRAY, "m Flown: ", TextColors.GOLD, distanceFlown, TextColors.GRAY, "m ", Text.NEW_LINE,
				"Swum: ", TextColors.GOLD, distanceSwum, TextColors.GRAY, "m Dove: ", TextColors.GOLD, distanceDived,
				TextColors.GRAY, "m ", Text.NEW_LINE, "Carted: ", TextColors.GOLD, distanceCarted, TextColors.GRAY,
				"m Rode: ", TextColors.GOLD, distanceRode, TextColors.GRAY, "m Boated: ", TextColors.GOLD,
				distanceBoated, TextColors.GRAY, "m ", Text.NEW_LINE, TextColors.DARK_GRAY,
				"(Excludes Creative Flying)"));

		distanceText.onHover(TextActions.showText(distanceTextHover.build()));
		statText.append(Text.of(Text.NEW_LINE, distanceText.build()));


		// Cake Slices Eaten
		statText.append(Text.of(Text.NEW_LINE,Strings.PREFIX, TextColors.GRAY, "Cake Slices Eaten: ", TextColors.GOLD, getStat(Statistics.CAKE_SLICES_EATEN, statMap)));

		src.sendMessage(statText.build());
		return CommandResult.empty();
	}

	/**
	 * Returns a correctly formatted time string from the provided number of
	 * seconds. Only shows seconds when they are less than a minute.
	 *
	 * @param seconds     Time in seconds
	 * @param showSeconds if true, print seconds when hours are not displayed
	 * @return Textual representation of time in english (E.g. 2 hours and 35
	 *         minutes)
	 */
	private String buildTime(long seconds, boolean showSeconds) {
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long displayMinutes = minutes % 60;
		long displaySeconds = seconds % 60;

		if (hours == 0) {
			// No hours, so display minutes and seconds
			if (displayMinutes == 0) {
				// No minutes, only display seconds
				return seconds + " seconds";
			}
			if (displaySeconds == 0 || !showSeconds) {
				return displayMinutes + " minutes";
			} else {
				return displayMinutes + " minutes and " + displaySeconds + " seconds";
			}
		}
		// Multiple hours, so only show hours and minutes
		if (displayMinutes == 0) {
			// No minutes, only display seconds
			return hours + " hours";
		}
		return hours + " hours and " + displayMinutes + " minutes";

	}

	/**
	 * Returns the long value of the provided statistic. If the statistic is
	 * non-existent, a value of 0 is returned.
	 *
	 * @param stat    Statistic to be retrieved.
	 * @param statMap Map containing statistics.
	 * @return Statistic if present, 0 otherwise.
	 */
	private long getStat(Statistic stat, Map<Statistic, Long> statMap) {
		Long statistic = statMap.get(stat);
		if (statistic == null) {
			return 0;
		} else {
			return statistic.longValue();
		}
	}
}
