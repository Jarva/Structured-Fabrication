package com.github.jarva.sf.common.block.entity;

import com.github.jarva.sf.setup.registry.DataComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
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
    private BlockState facadeState;

    public AbstractFacadeEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public BlockState getFacadeState() {
        return facadeState;
    }

    public void setFacadeState(BlockState state) {
        if (state != null && state.is(getBlockState().getBlock())) {
            return;
        }

        this.facadeState = state;

        this.requestModelDataUpdate();
        if (level != null) {
            level.blockEntityChanged(getBlockPos());
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput componentInput) {
        this.setFacadeState(componentInput.get(DataComponentRegistry.FACADE_STATE.get()));
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        BlockState state = getFacadeState();
        if (state != null) {
            components.set(DataComponentRegistry.FACADE_STATE.get(), getFacadeState());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        if (this.getFacadeState() != null) {
            tag.put("State", NbtUtils.writeBlockState(this.facadeState));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        if (tag.contains("State")) {
            this.facadeState = NbtUtils.readBlockState(registries.lookupOrThrow(BuiltInRegistries.BLOCK.key()), tag.getCompound("State"));
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        if (this.getFacadeState() != null) {
            tag.put("State", NbtUtils.writeBlockState(this.facadeState));
        }
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        if (tag.contains("State")) {
            this.facadeState = NbtUtils.readBlockState(registries.lookupOrThrow(BuiltInRegistries.BLOCK.key()), tag.getCompound("State"));
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull ModelData getModelData() {
        BlockState state = getFacadeState();
        ModelData.Builder builder = ModelData.builder();
        if (state != null) {
            builder = builder.with(BLOCK_STATE, state);
        }
        return builder.build();
    }
}
