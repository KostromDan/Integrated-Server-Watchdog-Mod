package dev.kostromdan.mods.integrated_server_watchdog.utils;

import java.util.ArrayDeque;

public class LastTicksDeque extends ArrayDeque<TickData> {
    /**
     * Stores data about last MAX_TICKS_COUNT ticks
     */

    final static int MAX_TICKS_COUNT = 50;

    @Override
    public void addFirst(TickData e) {
//        if (this.size() > 0 && this.getFirst().tickNumber == e.tickNumber) {
//            return;
//        }
        super.addFirst(e);
        if (this.size() > MAX_TICKS_COUNT) {
            this.removeLast();
        }
    }
}
