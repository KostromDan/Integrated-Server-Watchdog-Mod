package dev.kostromdan.mods.integrated_server_watchdog;

import dev.kostromdan.mods.integrated_server_watchdog.utils.LastTicksDeque;
import dev.kostromdan.mods.integrated_server_watchdog.utils.TickData;
import dev.kostromdan.mods.integrated_server_watchdog.watchdog.IntegratedWatchdog;
import net.minecraft.Util;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class IntegratedServerWatchdog {
    public static final Logger LOGGER = LogManager.getLogger("IntegratedServerWatchdog");

    public static final String MOD_ID = "integrated_server_watchdog";

    public static final LastTicksDeque LastTicks = new LastTicksDeque();

    public static void init() {
        // Write common init code here.
    }

    public void onServerStarted(MinecraftServer server) {
        IntegratedWatchdog watchdog = new IntegratedWatchdog(server);
        LastTicks.addFirst(new TickData(server));
        watchdog.start();
    }

    public void onServerTick(MinecraftServer server) {
        synchronized (LastTicks) {
            LastTicks.getFirst();
            LOGGER.info("{} {} {} {} {} {}",
                    server.getTickCount(),
                    LastTicks.getFirst().nextTickTime,
                    server.getNextTickTime(),
                    LastTicks.getFirst().curTime,
                    Util.getMillis(),
                    Util.getMillis() - LastTicks.getFirst().curTime);
            LastTicks.addFirst(new TickData(server));
        }
    }
}
