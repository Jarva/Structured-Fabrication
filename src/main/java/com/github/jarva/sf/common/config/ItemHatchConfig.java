package com.github.jarva.sf.common.config;

import com.github.jarva.sf.api.hatches.HatchConfig;
import com.github.jarva.sf.setup.registry.custom.ResourceTypeRegistry;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ItemHatchConfig extends HatchConfig {
    private final int defaultSlots;
    private ModConfigSpec.IntValue slots;

    public ItemHatchConfig(int defaultSlots) {
        super(ResourceTypeRegistry.ITEM_RESOURCE);
        this.defaultSlots = defaultSlots;
    }

    public Integer getSlots() {
        return slots.get();
    }

    @Override
    public ItemHatchConfig build(ModConfigSpec.Builder builder) {
        this.slots = builder.comment("Item Slots").defineInRange("slots", this.defaultSlots, 1, 54);

        return this;
    }
}
