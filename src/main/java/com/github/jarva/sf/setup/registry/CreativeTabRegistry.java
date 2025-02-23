package com.github.jarva.sf.setup.registry;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.hatches.HatchIO;
import com.github.jarva.sf.api.registry.HatchTiers;
import com.github.jarva.sf.api.registry.Machines;
import com.github.jarva.sf.api.registry.ResourceTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.stream.Stream;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, StructuredFabrication.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SF_TAB = CREATIVE_MODE_TABS.register("sf_tab", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("tab.sf"))
                    .icon(() -> BlockRegistry.CONTROLLER_BLOCK.asItem().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        Stream.concat(ItemRegistry.REGISTERED_ITEMS.stream(), BlockRegistry.REGISTERED_BLOCKS.stream()).forEach(output::accept);

                        parameters.holders().lookupOrThrow(Machines.MACHINE_REGISTRY_KEY).listElements().forEach(element -> {
                            ItemStack is = BlockRegistry.CONTROLLER_BLOCK.asItem().getDefaultInstance();
                            is.set(DataComponentRegistry.MACHINE, element.getDelegate());

                            output.accept(is);
                        });

                        parameters.holders().lookupOrThrow(ResourceTypes.RESOURCE_TYPE_REGISTRY_KEY).listElements().forEach(resource -> {
                            parameters.holders().lookupOrThrow(HatchTiers.HATCH_TIER_REGISTRY_KEY).listElements().forEach(tier -> {
                                ItemStack in = BlockRegistry.RESOURCE_HATCH_BLOCK.asItem().getDefaultInstance();
                                in.set(DataComponentRegistry.RESOURCE_TYPE, resource.value());
                                in.set(DataComponentRegistry.HATCH_TIER, tier.value());
                                in.set(DataComponentRegistry.HATCH_IO, HatchIO.INPUT);

                                ItemStack out = in.copy();
                                out.set(DataComponentRegistry.HATCH_IO, HatchIO.OUTPUT);

                                output.accept(in);
                                output.accept(out);
                            });
                        });
                    })
                    .build()
    );
}
