package com.github.jarva.sf.api.hatches;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.LanguageGeneration;
import com.github.jarva.sf.api.resources.ResourceType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;

public abstract class HatchTier implements LanguageGeneration {
    public abstract String getName();
    public abstract String getDescription();

    @Override
    public String getDescriptionId() {
        return "hatch_tier." + StructuredFabrication.MODID + "." + getName();
    }

    public final HashMap<ResourceLocation, HatchConfig> configs = new HashMap<>();

    public void addConfig(HatchConfig value) {
        configs.put(value.getId(), value);
    }

    public HatchConfig getConfig(DeferredHolder<ResourceType<?>, ? extends ResourceType<?>> type) {
        return configs.get(type.getId());
    }

    public HatchConfig getConfig(ResourceType<?> type) {
        return configs.get(type.getId());
    }
}
