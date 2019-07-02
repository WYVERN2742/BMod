package io.github.wyvern2742.bmod.event;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.world.chunk.UnloadChunkEvent;

import io.github.wyvern2742.bmod.BMod;
import io.github.wyvern2742.bmod.logic.Chunks;

/**
 * ChunkUnloadEvent
 */
public class ChunkUnloadEvent {

	BMod plugin;

	public ChunkUnloadEvent(BMod plugin) {
		this.plugin = plugin;
	}

	/**
	 * Simple Listener to log when a chunk is unloaded
	 * @param event Chunk Unload Event
	 */
	@Listener(order = Order.POST)
	public void onChunkUnload(UnloadChunkEvent event){
		Chunks.chunkUnload();
	}

}
