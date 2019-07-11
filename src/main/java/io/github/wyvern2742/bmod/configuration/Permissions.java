package io.github.wyvern2742.bmod.configuration;

/**
 * Permission nodes that control what aspects of commands are limited per user.
 * For example, most users would have the COMMAND_SPAWN permission node, however
 * only server administrators have the COMMAND_SPAWN_SET node.
 */
public class Permissions {
	// Test Command only available during development
	public static String COMMAND_TEST = "bmod.test";

	public static String COMMAND_HOME = "bmod.home";
	public static String COMMAND_SET_HOME = "bmod.home.set";
	public static String COMMAND_HELP = "bmod.help";
	public static String COMMAND_SPAWN = "bmod.spawn";
	public static String COMMAND_SPAWN_SET = "bmod.spawn.set";
	public static String COMMAND_STATUS = "bmod.status";
	public static String COMMAND_LIST = "bmod.list";
	public static String COMMAND_STATS = "bmod.stats";
	public static String COMMAND_PLUGINS = "bmod.plugins";
}
