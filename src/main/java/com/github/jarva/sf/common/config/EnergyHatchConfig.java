package com.github.jarva.sf.common.config;

import com.github.jarva.sf.api.hatches.HatchConfig;
import com.github.jarva.sf.setup.registry.custom.ResourceTypeRegistry;
import net.neoforged.neoforge.common.ModConfigSpec;

public class EnergyHatchConfig extends HatchConfig {
    private final int defaultCapacity;
    private final int defaultThroughput;
    private ModConfigSpec.IntValue capacity;
    private ModConfigSpec.IntValue throughput;

    public EnergyHatchConfig(int defaultCapacity, int defaultThroughput) {
        super(ResourceTypeRegistry.ENERGY_RESOURCE);
        this.defaultCapacity = defaultCapacity;
        this.defaultThroughput = defaultThroughput;
    }

    public Integer getCapacity() {
        return capacity.get();
    }

    public Integer getThroughput() {
        return throughput.get();
    }

    @Override
    public EnergyHatchConfig build(ModConfigSpec.Builder builder) {
        this.capacity = builder.comment("Energy Capacity").defineInRange("capacity", this.defaultCapacity, 1, Integer.MAX_VALUE);
        this.throughput = builder.comment("Energy Throughput").defineInRange("throughput", this.defaultThroughput, 1, Integer.MAX_VALUE);

        return this;
    }
}
