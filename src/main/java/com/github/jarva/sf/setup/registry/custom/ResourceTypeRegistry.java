package com.github.jarva.sf.setup.registry.custom;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.resources.ResourceType;
import com.github.jarva.sf.common.type.EnergyResourceType;
import com.github.jarva.sf.common.type.FluidResourceType;
import com.github.jarva.sf.common.type.ItemResourceType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.github.jarva.sf.api.registry.ResourceTypes.RESOURCE_TYPE_REGISTRY;

public class ResourceTypeRegistry {
    public static final DeferredRegister<ResourceType<?>> RESOURCE_TYPE = DeferredRegister.create(RESOURCE_TYPE_REGISTRY, StructuredFabrication.MODID);

    public static final DeferredHolder<ResourceType<?>, ItemResourceType> ITEM_RESOURCE = RESOURCE_TYPE.register("item", ItemResourceType::new);
    public static final DeferredHolder<ResourceType<?>, FluidResourceType> FLUID_RESOURCE = RESOURCE_TYPE.register("fluid", FluidResourceType::new);
    public static final DeferredHolder<ResourceType<?>, EnergyResourceType> ENERGY_RESOURCE = RESOURCE_TYPE.register("energy", EnergyResourceType::new);
}
