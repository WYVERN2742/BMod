package io.github.wyvern2742.bmod.logic;

/**
 * Chunks
 */
public class Chunks {

	private static int loadedChunks = 0;

	public static void chunkLoaded(){
		loadedChunks += 1;
	}

	public static void chunkUnload(){
		loadedChunks -= 1;
	}

	public static int loadedChunks(){
		return loadedChunks;
	}
}
