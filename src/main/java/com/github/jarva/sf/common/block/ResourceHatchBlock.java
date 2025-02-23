package com.github.jarva.sf.common.block;

import com.github.jarva.sf.common.block.entity.ControllerEntity;
import com.github.jarva.sf.common.block.entity.ResourceHatchEntity;
import com.github.jarva.sf.setup.registry.BlockRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ResourceHatchBlock extends Block implements EntityBlock {
    public static final MapCodec<ResourceHatchBlock> CODEC = simpleCodec(ResourceHatchBlock::new);

    public ResourceHatchBlock() {
        this(Properties.of());
    }

    public ResourceHatchBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<ResourceHatchBlock> codec() {
        return CODEC;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof ControllerEntity controller) {
            controller.applyComponentsFromItemStack(stack);
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof ControllerEntity controller) {
            controller.validate(level, pos, state);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ResourceHatchEntity(pos, state);
    }
}
