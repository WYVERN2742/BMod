package io.github.wyvern2742.bmod.event;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.world.chunk.LoadChunkEvent;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.logic.Chunks;

/**
 * ChunkLoadEvent
 */
public class ChunkLoadEvent {

	BMod plugin;

	public ChunkLoadEvent(BMod plugin) {
		this.plugin = plugin;
	}

	/**
	 * Simple Listener to log when a chunk is loaded
	 * @param event Chunk Load Event
	 */
	@Listener(order = Order.POST)
	public void onChunkLoad(LoadChunkEvent event){
		Chunks.chunkLoaded();
	}
}
