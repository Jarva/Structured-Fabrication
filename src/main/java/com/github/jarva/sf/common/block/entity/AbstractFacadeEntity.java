package com.github.jarva.sf.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractFacadeEntity extends BlockEntity {
    public static final ModelProperty<BlockState> BLOCK_STATE = new ModelProperty<>();
    private BlockState state = null;

    public AbstractFacadeEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public BlockState getFacadeState() {
        return this.state;
    }

    public void setFacadeState(BlockState state) {
        if (state != null && state.is(getBlockState().getBlock())) {
            return;
        }
        this.state = state;
        this.requestModelDataUpdate();
        if (level != null) {
            level.blockEntityChanged(getBlockPos());
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        BlockState state = getFacadeState();
        if (state != null) {
             tag.put("State", NbtUtils.writeBlockState(state));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        if (tag.contains("State")) {
            BlockState state = NbtUtils.readBlockState(
                    registries.asGetterLookup().lookupOrThrow(BuiltInRegistries.BLOCK.key()),
                    tag.getCompound("State")
            );
            setFacadeState(state);
        }
    }

    @Override
    public @NotNull ModelData getModelData() {
        ModelData data = ModelData.builder()
                .with(BLOCK_STATE, getFacadeState())
                .build();
        return data;
    }
}
