package com.github.jarva.sf.common.type;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.hatches.HatchConfig;
import com.github.jarva.sf.api.resources.ResourceStorage;
import com.github.jarva.sf.api.resources.ResourceType;
import com.github.jarva.sf.api.hatches.HatchTier;
import com.github.jarva.sf.common.config.ItemHatchConfig;
import com.github.jarva.sf.setup.registry.custom.ResourceTypeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ItemResourceType extends ResourceType<ItemResourceType.Storage> {
    @Override
    public Storage getStorageForTier(HatchTier tier) {
        HatchConfig hatch = tier.getConfig(ResourceTypeRegistry.ITEM_RESOURCE);
        if (hatch instanceof ItemHatchConfig config) {
            return new Storage(tier, config.getSlots());
        }
        return null;
    }

    @Override
    public String getName() {
        return "item";
    }

    @Override
    public String getDescription() {
        return "Item";
    }

    public static class Storage extends ResourceStorage<ItemStackHandler> {
        public Storage(HatchTier tier, int slots) {
            super(tier, new ItemStackHandler(slots));
        }

        @Override
        public CompoundTag serializeNBT(HolderLookup.Provider registries) {
            return this.storage.serializeNBT(registries);
        }

        @Override
        public void deserializeNBT(HolderLookup.Provider registries, CompoundTag tag) {
            this.storage.deserializeNBT(registries, tag);
        }
    }
}
