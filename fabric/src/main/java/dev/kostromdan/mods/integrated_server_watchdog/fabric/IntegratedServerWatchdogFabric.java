package dev.kostromdan.mods.integrated_server_watchdog.fabric;

import net.fabricmc.api.ModInitializer;

import dev.kostromdan.mods.integrated_server_watchdog.IntegratedServerWatchdog;

public final class IntegratedServerWatchdogFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        IntegratedServerWatchdog.init();
    }
}
