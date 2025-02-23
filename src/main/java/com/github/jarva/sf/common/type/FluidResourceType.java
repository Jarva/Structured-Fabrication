package com.github.jarva.sf.common.type;

import com.github.jarva.sf.api.hatches.HatchConfig;
import com.github.jarva.sf.api.resources.ResourceStorage;
import com.github.jarva.sf.api.resources.ResourceType;
import com.github.jarva.sf.api.hatches.HatchTier;
import com.github.jarva.sf.common.config.FluidHatchConfig;
import com.github.jarva.sf.setup.registry.custom.ResourceTypeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class FluidResourceType extends ResourceType<FluidResourceType.Storage> {
    @Override
    public Storage getStorageForTier(HatchTier tier) {
        HatchConfig hatch = tier.getConfig(ResourceTypeRegistry.FLUID_RESOURCE);
        if (hatch instanceof FluidHatchConfig config) {
            return new Storage(tier, config.getCapacity());
        }
        return null;
    }

    @Override
    public String getName() {
        return "fluid";
    }

    @Override
    public String getDescription() {
        return "Fluid";
    }

    public static class Storage extends ResourceStorage<FluidTank> {
        public Storage(HatchTier tier, int capacity) {
            super(tier, new FluidTank(capacity));
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.Provider registries) {
            return this.storage.writeToNBT(registries, new CompoundTag());
        }

        @Override
        public void deserializeNBT(HolderLookup.Provider registries, CompoundTag tag) {
            this.storage.readFromNBT(registries, tag);
        }
    }

}
