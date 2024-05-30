package dev.kostromdan.mods.integrated_server_watchdog.utils;

import net.minecraft.Util;
import net.minecraft.server.MinecraftServer;

public class TickData {
    public int tickNumber;
    public long curTime;
    public long nextTickTime;

    public TickData(int tickNumber, long nextTickTime) {
        this.tickNumber = tickNumber;
        this.nextTickTime = nextTickTime;
        this.curTime = Util.getMillis();
    }

    public TickData(MinecraftServer server) {
        this(server.getTickCount(), server.getNextTickTime());
    }
}
