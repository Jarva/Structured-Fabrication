package com.github.jarva.sf.setup.registry;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.registry.HatchTiers;
import com.github.jarva.sf.api.registry.Machines;
import com.github.jarva.sf.api.registry.ResourceIngredients;
import com.github.jarva.sf.api.registry.ResourceTypes;
import com.github.jarva.sf.common.machines.Machine;
import com.github.jarva.sf.setup.registry.custom.HatchTierRegistry;
import com.github.jarva.sf.setup.registry.custom.ResourceIngredientRegistry;
import com.github.jarva.sf.setup.registry.custom.ResourceTypeRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = StructuredFabrication.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegistrySetup {
    public static void register(IEventBus modEventBus) {
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockRegistry.BLOCK_ENTITIES.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        DataComponentRegistry.DATA_COMPONENTS.register(modEventBus);

        HatchTierRegistry.HATCH_TIER.register(modEventBus);
        ResourceTypeRegistry.RESOURCE_TYPE.register(modEventBus);
        ResourceIngredientRegistry.RESOURCE_INGREDIENT.register(modEventBus);
    }

    @SubscribeEvent
    public static void register(NewRegistryEvent event) {
        event.register(HatchTiers.HATCH_TIER_REGISTRY);
        event.register(ResourceTypes.RESOURCE_TYPE_REGISTRY);
        event.register(ResourceIngredients.RESOURCE_INGREDIENT_REGISTRY);
    }

    @SubscribeEvent
    public static void register(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Machines.MACHINE_REGISTRY_KEY, Machine.DIRECT_CODEC, Machine.DIRECT_CODEC);
    }

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {

    }
}
