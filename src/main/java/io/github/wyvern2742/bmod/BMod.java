package io.github.wyvern2742.bmod;

import java.sql.SQLException;

import com.google.inject.Inject;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.command.spec.CommandSpec.Builder;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import io.github.wyvern2742.bmod.commands.AbstractCommand;
import io.github.wyvern2742.bmod.commands.HelpCommand;
import io.github.wyvern2742.bmod.commands.HomeCommand;
import io.github.wyvern2742.bmod.commands.ListCommand;
import io.github.wyvern2742.bmod.commands.SetHomeCommand;
import io.github.wyvern2742.bmod.commands.SetSpawnCommand;
import io.github.wyvern2742.bmod.commands.SpawnCommand;
import io.github.wyvern2742.bmod.commands.StatsCommand;
import io.github.wyvern2742.bmod.commands.StatusCommand;
import io.github.wyvern2742.bmod.configuration.database.DatabaseManager;

@Plugin(id = "bmod", name = "BMod", version = "0.1", description = "All you ever need for a server.")
public class BMod {

	@Inject
	public Logger logger;
	private AbstractCommand[] commands;

	@Listener
	public void preInit(GamePreInitializationEvent event) {
		try {
			DatabaseManager manager = DatabaseManager.getInstance();
			if (!manager.databaseExists()) {
				manager.constructDatabase();
				logger.info("Database Created");
			}
		} catch (SQLException e) {
			logger.error("Unable to create database", e);
		}
	}

	@Listener
	public void onServerStart(GameStartedServerEvent event) {
		logger.info("Registering Commands");
		registerCommands();
		logger.info("Loaded with " + commands.length + " commands");
	}

	private void registerCommands() {

		this.commands = new AbstractCommand[] { new HelpCommand(this), new SetSpawnCommand(this), new HomeCommand(this),
				new ListCommand(this), new StatusCommand(this), new StatsCommand(this), new SetHomeCommand(this),
				new SpawnCommand(this) };

		// Register commands
		for (AbstractCommand command : commands) {
			// Create commandspec from command object
			Builder cmdspec = CommandSpec.builder().executor(command).description(command.getSummery())
					.permission(command.getPermission()).extendedDescription(command.getDescription());

			if (command.hasArgs()) {
				cmdspec.arguments(command.getArgs());
			}

			// Register with sponge
			Sponge.getCommandManager().register(this, cmdspec.build(), (String[]) command.getAliases());
		}
	}

	public AbstractCommand[] getCommands() {
		return commands;
	}
}
