package com.github.jarva.sf.api.resources;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.LanguageGeneration;
import com.github.jarva.sf.api.registry.ResourceTypes;
import com.github.jarva.sf.api.hatches.HatchTier;
import net.minecraft.resources.ResourceLocation;

public abstract class ResourceType<T extends ResourceStorage<?>> implements LanguageGeneration {
    public abstract T getStorageForTier(HatchTier tier);

    public ResourceLocation getId() {
        return ResourceTypes.RESOURCE_TYPE_REGISTRY.getKey(this);
    }

    @Override
    public String getDescriptionId() {
        return "resource_type." + StructuredFabrication.MODID + "." + getName();
    }
}
