package com.github.jarva.sf.datagen;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.datagen.client.BlockStateDatagen;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = StructuredFabrication.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        gen.addProvider(event.includeServer(), new BlockStateDatagen(gen.getPackOutput(), event.getExistingFileHelper()));
        gen.addProvider(event.includeServer(), new MachineDatagen(gen.getPackOutput(), event.getLookupProvider()));
        gen.addProvider(event.includeServer(), new LangDatagen(gen.getPackOutput(), "en_us"));
    }
}
