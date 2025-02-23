package com.github.jarva.sf.common.config;

import com.github.jarva.sf.api.hatches.HatchConfig;
import com.github.jarva.sf.setup.registry.custom.ResourceTypeRegistry;
import net.neoforged.neoforge.common.ModConfigSpec;

public class FluidHatchConfig extends HatchConfig {
    private final int defaultCapacity;
    private ModConfigSpec.IntValue capacity;

    public FluidHatchConfig(int defaultCapacity) {
        super(ResourceTypeRegistry.FLUID_RESOURCE);
        this.defaultCapacity = defaultCapacity;
    }

    public Integer getCapacity() {
        return capacity.get();
    }

    @Override
    public FluidHatchConfig build(ModConfigSpec.Builder builder) {
        this.capacity = builder.comment("Fluid Capacity").defineInRange("capacity", this.defaultCapacity, 1, Integer.MAX_VALUE);

        return this;
    }
}
