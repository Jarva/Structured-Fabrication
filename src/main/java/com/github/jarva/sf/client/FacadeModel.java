package com.github.jarva.sf.client;

import com.github.jarva.sf.StructuredFabricationClient;
import com.github.jarva.sf.common.block.entity.AbstractFacadeEntity;
import com.github.jarva.sf.setup.registry.DataComponentRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FacadeModel implements IDynamicBakedModel {
    private static final Material MISSING_TEXTURE =
            new Material(InventoryMenu.BLOCK_ATLAS, MissingTextureAtlasSprite.getLocation());
    private final BakedModel base;
    private final BlockState facade;

    public FacadeModel(BakedModel base) {
        this.base = base;
        this.facade = null;
    }

    public FacadeModel(BakedModel base, BlockState facade) {
        this.base = base;
        this.facade = facade;
    }

    public BlockState getFacade(ModelData data) {
        if (facade != null) {
            return facade;
        }
        if (data.has(AbstractFacadeEntity.BLOCK_STATE)) {
            @Nullable BlockState state = data.get(AbstractFacadeEntity.BLOCK_STATE);
            return state;
        }
        return null;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, RandomSource randomSource, ModelData modelData, @Nullable RenderType renderType) {
        List<BakedQuad> quads = new ArrayList<>();
        BlockState state = getFacade(modelData);
        if (state != null) {
            quads.addAll(Minecraft.getInstance().getBlockRenderer().getBlockModel(state).getQuads(state, direction, randomSource, modelData, renderType));
            return quads;
        }

        quads.addAll(base.getQuads(blockState, direction, randomSource, modelData, renderType));
        return quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean usesBlockLight() {
        return true;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return MISSING_TEXTURE.sprite();
    }

    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }

//    @Override
//    public ItemTransforms getTransforms() {
//        return StructuredFabricationClient.modelOf(StructuredFabricationClient.MACHINE_CASING).getTransforms();
//    }
//
//    @Override
//    public List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous) {
//        BlockState state = itemStack.get(DataComponentRegistry.FACADE_STATE);
//        if (state == null) {
//            return List.of(RenderType.solid());
//        }
//
//        return Minecraft.getInstance()
//                .getItemRenderer()
//                .getModel(state.getBlock().asItem().getDefaultInstance(), null, null, 0)
//                .getRenderTypes(itemStack, fabulous);
//    }
//
//    @Override
//    public @NotNull List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
//        @Nullable BlockState state = itemStack.get(DataComponentRegistry.FACADE_STATE);
//        if (state == null) {
//            return List.of(this);
//        }
//
//        return List.of(new FacadeModel(base, state));
//    }
}
