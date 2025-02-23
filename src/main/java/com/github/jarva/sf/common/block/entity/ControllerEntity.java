package com.github.jarva.sf.common.block.entity;

import com.github.jarva.sf.common.machines.Machine;
import com.github.jarva.sf.setup.registry.BlockRegistry;
import com.github.jarva.sf.setup.registry.DataComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class ControllerEntity extends AbstractFacadeEntity {
    private final List<BlockPos> hatches = new ArrayList<>();
    private boolean valid;

    public ControllerEntity(BlockPos pos, BlockState blockState) {
        super(BlockRegistry.CONTROLLER_ENTITY.get(), pos, blockState);
    }

    public boolean addHatch(BlockPos pos) {
        if (this.level == null) return false;

        BlockEntity be = this.level.getBlockEntity(pos);
        if (be == null) return false;

        if (this.level instanceof ServerLevel serverLevel) {
            serverLevel.registerCapabilityListener(pos, () -> removeHatch(pos));
        }
        return hatches.add(pos);
    }

    public boolean removeHatch(BlockPos pos) {
        return hatches.remove(pos);
    }

    public boolean validate() {
        return validate(this.level, getBlockPos(), getBlockState());
    }

    public boolean validate(Level level, BlockPos pos, BlockState state) {
        Holder<Machine> machine = this.components().get(DataComponentRegistry.MACHINE.get());
        if (machine == null) return false;

        if (level instanceof ServerLevel serverLevel) {
            this.valid = machine.value().getInfo().validate(serverLevel, getBlockPos(), getBlockState());
        }
        return this.valid;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        ListTag hatches = new ListTag();
        for (BlockPos pos : this.hatches) {
            hatches.add(NbtUtils.writeBlockPos(pos));
        }
        tag.put("Hatches", hatches);
        tag.putBoolean("Valid", this.valid);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        ListTag hatches = tag.getList("Hatches", 11);
        for (int i = 0; i < hatches.size(); i++) {
            int[] pos = hatches.getIntArray(i);
            this.addHatch(new BlockPos(pos[0], pos[1], pos[2]));
        }
        this.valid = tag.getBoolean("Valid");
    }

    public static <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState state, T entity) {

    }
}
