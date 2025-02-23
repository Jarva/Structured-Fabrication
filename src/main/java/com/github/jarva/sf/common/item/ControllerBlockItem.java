package com.github.jarva.sf.common.item;

import com.github.jarva.sf.common.machines.Machine;
import com.github.jarva.sf.setup.registry.DataComponentRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class ControllerBlockItem extends BlockItem {
    public ControllerBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        @Nullable Holder<Machine> machine = stack.get(DataComponentRegistry.MACHINE);
        Component component = super.getName(stack);
        if (machine == null) {
            return Component.translatable("label.sf.base", component);
        }
        return Component.literal(machine.value().name()).append(" ").append(component);
    }
}
