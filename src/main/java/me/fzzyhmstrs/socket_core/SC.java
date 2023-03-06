package me.fzzyhmstrs.socket_core;

import me.fzzyhmstrs.socket_core.registry.SocketRegistries;
import me.fzzyhmstrs.socket_core.socket.SocketStack;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SC implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "socket_core";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		SocketRegistries.init();
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
			List<SocketStack> socketStacks = stack.getSockets();
			for (SocketStack socketStack : socketStacks){
				lines.addAll(socketStack.getTooltip());
			}
		});
	}
}