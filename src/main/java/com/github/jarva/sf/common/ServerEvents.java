package com.github.jarva.sf.common;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.common.machines.Machine;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@EventBusSubscriber(modid = StructuredFabrication.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerEvents {
    @SubscribeEvent
    private static void serverStarted(ServerStartedEvent event) {
        Machine.loadStructures(event.getServer());
    }
}
