package dev.kostromdan.mods.integrated_server_watchdog.watchdog;

import com.mojang.logging.LogUtils;
import dev.kostromdan.mods.integrated_server_watchdog.IntegratedServerWatchdog;
import net.minecraft.DefaultUncaughtExceptionHandlerWithName;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

import java.lang.ref.WeakReference;

public class IntegratedWatchdog extends Thread {
    private static final Logger LOGGER = LogUtils.getLogger();

    private final WeakReference<MinecraftServer> server;

    private static final long MAX_TICK_DELTA = 1000; //  40*1000

    public IntegratedWatchdog(MinecraftServer server) {
        this.server = new WeakReference<>(server);
        this.setDaemon(true);
        this.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandlerWithName(LOGGER));
        this.setName("Integrated Server Watchdog");
    }

    public void run() {
        while (true) {
            MinecraftServer server = this.server.get();
            if (server == null || !server.isRunning())
                return;
            long curTime = Util.getMillis();
            long nextTickTime = server.getNextTickTime();
            long curTimeOfLastTick;
            long nextTickTimeOfLastTick;
            synchronized (IntegratedServerWatchdog.LastTicks) {
                curTimeOfLastTick = IntegratedServerWatchdog.LastTicks.getFirst().curTime;
                nextTickTimeOfLastTick = IntegratedServerWatchdog.LastTicks.getFirst().nextTickTime;
            }

            long delta = curTime - curTimeOfLastTick;
            if (delta > MAX_TICK_DELTA && nextTickTimeOfLastTick == nextTickTime) {
                Minecraft.getInstance().gui.getChat().addMessage(Component.literal(String.format(
                        "A single server tick took more when %.2f seconds(should be max 0.05). Seems like integrated server is under extreme lag or deadlocked. [Learn more] [Crash game] [Return to main screen] [Open log]",
                        (double) delta / 1000)));
                LOGGER.error("A single server tick has taken {}, more than {} milliseconds", delta, MAX_TICK_DELTA);
                LOGGER.error(ThreadDumper.obtainThreadDump());
            }
            server = null; /* allow GC */
            try {
                Thread.sleep(MAX_TICK_DELTA);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
