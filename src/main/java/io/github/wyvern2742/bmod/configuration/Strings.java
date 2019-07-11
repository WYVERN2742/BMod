package io.github.wyvern2742.bmod.configuration;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * Contains function to deal with text within the plugin,
 * also contains methods used to format text.
 */
public class Strings {
	/** How many characters Minecraft's default chat width fits */
	public static int MINECRAFT_CHAT_WIDTH = 53;

	/** Standard Chat Prefix */
	public static Text PREFIX = Text.of(TextColors.GOLD,"> ",TextColors.GRAY);

	// Command summery should be about 40 characters long.
	public static Text COMMAND_HELP_SUMMERY = Text.of("Show BMod's command list");
	public static Text COMMAND_HELP_DESCRIPTION = Text.of("Shows all commands registered by BMod, along with summaries of each command.");

	public static Text COMMAND_SPAWN_SET_SUMMERY = Text.of("Set the server's spawn point");
	public static Text COMMAND_SPAWN_SET_DESCRIPTION = Text.of("Marks your position as the server's spawn point. Players can also use the spawn command to teleport here.");

	public static Text COMMAND_STATUS_SUMMERY = Text.of("Show server status");
	public static Text COMMAND_STATUS_DESCRIPTION = Text.of("Shows the server's technical status, including used ram, loaded chunks and ping.");

	public static Text COMMAND_LIST_SUMMERY = Text.of("Show who is currently playing");
	public static Text COMMAND_LIST_DESCRIPTION = Text.of("Show a list of all players on the server, including their ranks and factions.");

	public static Text COMMAND_STATS_SUMMERY = Text.of("View cool statistics about a player");
	public static Text COMMAND_STATS_DESCRIPTION = Text.of("See playtime, player kills, mob kills, deaths, etc about any player.");

	public static Text COMMAND_HOME_SUMMERY = Text.of("Teleport home");
	public static Text COMMAND_HOME_DESCRIPTION = Text.of("Teleport to a home that's been set. Works across dimensions. A home needs to be set first.");

	public static Text COMMAND_SPAWN_SUMMERY = Text.of("Teleport to spawn");
	public static Text COMMAND_SPAWN_DESCRIPTION = Text.of("Teleport to the server's spawn. Works across dimensions.");

	public static Text COMMAND_SET_HOME_SUMMERY = Text.of("Sets your home");
	public static Text COMMAND_SET_HOME_DESCRIPTION = Text.of("Set your home to the current location. This can be teleported to by using the home command.");

	public static Text COMMAND_TEST_SUMMERY = Text.of("Developer Test Command");
	public static Text COMMAND_TEST_DESCRIPTION = Text.of("Only Available during Development");

	public static Text COMMAND_PLUGINS_SUMMERY = Text.of("List server plugins");
	public static Text COMMAND_PLUGINS_DESCRIPTION = Text.of("View a list of loaded server plugins");

	/**
	 * Checks to see weather the provided number of characters will overflow
	 * the default Minecraft Textbox size.
	 * <p>
	 * Standard usage is for checking weather to collapse or expand lists
	 * @param prefixLength Length of the content's prefix
	 * @param contentLength Length of possible content
	 * @return True if the listLength overflows the line
	 */
	public static boolean overflowsLine(int prefixLength, int contentLength) {
		return MINECRAFT_CHAT_WIDTH - prefixLength - contentLength < 0;
	}
}
