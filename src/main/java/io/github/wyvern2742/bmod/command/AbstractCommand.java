package io.github.wyvern2742.bmod.command;

import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import io.github.wyvern2742.bmod.BMod;

/**
 * Represents a command that a player, or console can use.
 * Contains information related to the command, such as {@link CommandExecutor},
 * Permission, Description, and possible aliases.
 */
abstract public class AbstractCommand implements CommandExecutor {

	protected final BMod plugin;
	protected String[] aliases;

	// Note: summery should be 43 - aliases[1] characters in length
	protected Text summery;
	protected Text description;

	protected CommandElement args;
	protected String permission;

	public AbstractCommand(BMod plugin, String[] aliases, Text summery, Text description, String permission) {
		this.plugin = plugin;
		this.aliases = aliases;
		this.summery = summery;
		this.description = description;
		this.permission = permission;
	}

	public AbstractCommand(BMod plugin, String[] aliases, Text summery, String permission) {
		this(plugin, aliases, summery, Text.EMPTY, permission);
	}

	public AbstractCommand(BMod plugin, String alias, Text summery, String permission) {
		this(plugin, new String[]{alias}, summery, permission);
	}

	public AbstractCommand(BMod plugin, String alias, Text summery, Text description, String permission) {
		this(plugin, new String[]{alias}, summery, description, permission);
	}

	/**
	 * @return Command permission string
	 */
	public String getPermission(){
		return this.permission;
	}

	/**
	 * @return Command Summery Description
	 * @see AbstractCommand#getDescription()
	 */
	public Text getSummery() {
		return summery;
	}

	/**
	 * @return Command Extended Description
	 * @see AbstractCommand#getSummery()
	 */
	public Text getDescription(){
		return description;
	}

	/**
	 * @return Command arguments
	 */
	public CommandElement getArgs() {
		return args;
	}

	/**
	 * @return Possible command aliases
	 */
	public String[] getAliases() {
		return aliases;
	}

	public boolean hasArgs(){
		return !(args==null);
	}
}
