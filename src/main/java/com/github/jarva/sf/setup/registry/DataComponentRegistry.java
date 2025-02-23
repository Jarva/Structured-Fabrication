package com.github.jarva.sf.setup.registry;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.hatches.HatchTier;
import com.github.jarva.sf.api.hatches.HatchIO;
import com.github.jarva.sf.api.registry.HatchTiers;
import com.github.jarva.sf.api.registry.ResourceTypes;
import com.github.jarva.sf.api.resources.ResourceType;
import com.github.jarva.sf.common.machines.Machine;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DataComponentRegistry {
    public static DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, StructuredFabrication.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Holder<Machine>>> MACHINE = DATA_COMPONENTS.register("machine",
            () -> DataComponentType.<Holder<Machine>>builder().persistent(Machine.CODEC).build()
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceType<?>>> RESOURCE_TYPE = DATA_COMPONENTS.register("resource_type",
            () -> DataComponentType.<ResourceType<?>>builder().persistent(ResourceTypes.RESOURCE_TYPE_REGISTRY.byNameCodec()).build()
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<HatchTier>> HATCH_TIER = DATA_COMPONENTS.register("hatch_tier",
            () -> DataComponentType.<HatchTier>builder().persistent(HatchTiers.HATCH_TIER_REGISTRY.byNameCodec()).build()
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<HatchIO>> HATCH_IO = DATA_COMPONENTS.register("hatch_io",
            () -> DataComponentType.<HatchIO>builder().persistent(HatchIO.CODEC).build()
    );
}
