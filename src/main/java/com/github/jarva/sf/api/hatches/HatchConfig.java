package com.github.jarva.sf.api.hatches;

import com.github.jarva.sf.api.resources.ResourceType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.registries.DeferredHolder;

public abstract class HatchConfig {
    private final DeferredHolder<ResourceType<?>, ? extends ResourceType<?>> type;

    public HatchConfig(DeferredHolder<ResourceType<?>, ? extends ResourceType<?>> type) {
        this.type = type;
    }

    public ResourceLocation getId() {
        return type.getId();
    }

    public ResourceType<?> getType() {
        return type.get();
    }

    public abstract HatchConfig build(ModConfigSpec.Builder builder);
}
