package com.github.jarva.sf.common.item;

import com.github.jarva.sf.api.hatches.HatchIO;
import com.github.jarva.sf.api.hatches.HatchTier;
import com.github.jarva.sf.api.resources.ResourceType;
import com.github.jarva.sf.common.machines.Machine;
import com.github.jarva.sf.setup.registry.DataComponentRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class ResourceHatchBlockItem extends BlockItem {
    public ResourceHatchBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        @Nullable HatchTier tier = stack.get(DataComponentRegistry.HATCH_TIER);
        @Nullable ResourceType<?> type = stack.get(DataComponentRegistry.RESOURCE_TYPE);
        @Nullable HatchIO io = stack.get(DataComponentRegistry.HATCH_IO);
        if (tier != null && type != null && io != null) {
            return Component.translatable(
                    getDescriptionId(stack) + ".bound",
                    Component.translatable(tier.getDescriptionId()),
                    Component.translatable(type.getDescriptionId()),
                    Component.translatable(io.getDescriptionId())
            );
        }
        return Component.translatable("label.sf.base", super.getName(stack));
    }
}
