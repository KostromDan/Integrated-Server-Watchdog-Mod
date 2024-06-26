package dev.kostromdan.mods.integrated_server_watchdog.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import dev.kostromdan.mods.integrated_server_watchdog.IntegratedServerWatchdog;

@Mod(IntegratedServerWatchdog.MOD_ID)
public final class IntegratedServerWatchdogForge {
    public IntegratedServerWatchdogForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(IntegratedServerWatchdog.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.register(new IntegratedServerWatchdogClientForge()));


        // Run our common setup.
        IntegratedServerWatchdog.init();
    }
}
