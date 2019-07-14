package io.github.wyvern2742.bmod.command;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.configuration.Permissions;
import io.github.wyvern2742.bmod.configuration.Strings;
import io.github.wyvern2742.bmod.exception.PlayerNoPermissionException;

/**
 * List loaded plugins
 */
public class PluginCommand extends AbstractCommand {

	public PluginCommand(BMod plugin) {
		super(plugin, new String[] { "plugins", "pl" }, Strings.COMMAND_PLUGINS_SUMMERY,
				Strings.COMMAND_PLUGINS_DESCRIPTION, Permissions.COMMAND_PLUGINS);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		try {
			src.sendMessage(getPlugins(src, !(src instanceof Player)));
			return CommandResult.success();
		} catch (PlayerNoPermissionException e) {
			src.sendMessage(Text.of(TextColors.RED, e.getMessage()));
			return CommandResult.empty();
		}
	}

	public static Text getPlugins(CommandSource src) throws PlayerNoPermissionException {
		return getPlugins(src, false);
	}

	public static Text getPlugins(CommandSource src, boolean longForm) throws PlayerNoPermissionException {
		boolean supportsHover = src instanceof Player;

		if (src instanceof Player) {
			if (!src.hasPermission(Permissions.COMMAND_PLUGINS)) {
				throw new PlayerNoPermissionException("Cannot list plugins without " + Permissions.COMMAND_PLUGINS);
			}
		}

		PluginContainer[] plugins = Sponge.getPluginManager().getPlugins()
				.toArray(new PluginContainer[Sponge.getPluginManager().getPlugins().size()]);

		if (longForm) {
			return buildLongForm(plugins);
		}

		Builder basicText = Text.builder();
		basicText.append(Text.of(Strings.PREFIX, TextColors.GRAY, "In total, there are ", TextColors.GOLD,
				plugins.length, TextColors.GRAY, " plugins enabled"));

		if (supportsHover) {
			basicText.onHover(TextActions.showText(buildLongForm(plugins)));
			return basicText.build();
		} else {
			return basicText.build();
		}
	}

	private static Text buildLongForm(PluginContainer[] plugins) {
		Builder longText = Text.builder();

		for (int i = 0; i < plugins.length; i++) {
			PluginContainer plugin = plugins[i];
			if (plugin.getVersion().isPresent()) {
				longText.append(
						Text.of(TextColors.GOLD, plugin.getName(), TextColors.GRAY, " v", plugin.getVersion().get()));
			} else {
				longText.append(Text.of(TextColors.GOLD, plugin.getName()));
			}
			if (i < plugins.length - 1) {
				longText.append(Text.NEW_LINE);
			}
		}
		return longText.build();
	}
}
