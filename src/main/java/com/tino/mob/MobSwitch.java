package com.tino.mob;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobSwitch implements ModInitializer {
	public static final String MOD_ID = "mob-switch";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	// The custom BlockEntity registration was removed to keep the mod purely
	// server-side.
	// We now use a world SavedData component to track Inhibitor Core positions
	// instead.

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Mob Switch Mod");
	}
}