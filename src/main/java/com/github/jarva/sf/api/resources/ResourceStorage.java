package com.github.jarva.sf.api.resources;

import com.github.jarva.sf.api.hatches.HatchTier;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public abstract class ResourceStorage<T> implements INBTSerializable<CompoundTag> {
    public final T storage;
    private final HatchTier tier;

    public ResourceStorage(HatchTier tier, T storage) {
        this.tier = tier;
        this.storage = storage;
    }

    public HatchTier getTier() {
        return this.tier;
    }

    public T getCapabilityHandler() {
        return this.storage;
    }

    abstract public CompoundTag serializeNBT(HolderLookup.Provider registries);
    abstract public void deserializeNBT(HolderLookup.Provider registries, CompoundTag tag);
}
