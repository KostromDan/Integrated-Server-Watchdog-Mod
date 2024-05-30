package dev.kostromdan.mods.integrated_server_watchdog.forge;

import dev.kostromdan.mods.integrated_server_watchdog.IntegratedServerWatchdog;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class IntegratedServerWatchdogClientForge {
    private static IntegratedServerWatchdog commonMod;

    public IntegratedServerWatchdogClientForge() {
        commonMod = new IntegratedServerWatchdog();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartedEvent event) {
        commonMod.onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        commonMod.onServerTick(event.getServer());
    }

}
