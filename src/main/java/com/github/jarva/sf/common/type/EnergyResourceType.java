package com.github.jarva.sf.common.type;

import com.github.jarva.sf.api.hatches.HatchConfig;
import com.github.jarva.sf.api.resources.ResourceStorage;
import com.github.jarva.sf.api.resources.ResourceType;
import com.github.jarva.sf.api.hatches.HatchTier;
import com.github.jarva.sf.common.config.EnergyHatchConfig;
import com.github.jarva.sf.common.config.FluidHatchConfig;
import com.github.jarva.sf.setup.registry.custom.ResourceTypeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.energy.EnergyStorage;

public class EnergyResourceType extends ResourceType<EnergyResourceType.Storage> {
    @Override
    public Storage getStorageForTier(HatchTier tier) {
        HatchConfig hatch = tier.getConfig(ResourceTypeRegistry.ENERGY_RESOURCE);
        if (hatch instanceof EnergyHatchConfig config) {
            return new Storage(tier, config.getCapacity(), config.getThroughput());
        }
        return null;
    }

    @Override
    public String getName() {
        return "energy";
    }

    @Override
    public String getDescription() {
        return "Energy";
    }

    public static class Storage extends ResourceStorage<EnergyStorage> {
        public Storage(HatchTier tier, int capacity, int throughput) {
            super(tier, new EnergyStorage(capacity, throughput));
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.Provider registries) {
            Tag amount = this.storage.serializeNBT(registries);
            CompoundTag tag = new CompoundTag();
            tag.put("amount", amount);
            return tag;
        }

        @Override
        public void deserializeNBT(HolderLookup.Provider registries, CompoundTag nbt) {
            if (nbt.contains("amount")) {
                this.storage.deserializeNBT(registries, nbt.get("amount"));
            }
        }
    }

}
