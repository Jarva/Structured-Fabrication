package com.github.jarva.sf.client;

import com.github.jarva.sf.StructuredFabricationClient;
import com.github.jarva.sf.common.block.entity.AbstractFacadeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
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

    public FacadeModel(BakedModel base) {
        this.base = base;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, RandomSource randomSource, ModelData modelData, @Nullable RenderType renderType) {
        List<BakedQuad> quads = new ArrayList<>();
        if (modelData.has(AbstractFacadeEntity.BLOCK_STATE)) {
            @Nullable BlockState state = modelData.get(AbstractFacadeEntity.BLOCK_STATE);
            if (state != null) {
                quads.addAll(Minecraft.getInstance().getBlockRenderer().getBlockModel(state).getQuads(state, direction, randomSource, modelData, renderType));
                return quads;
            }
        }
        quads.addAll(base.getQuads(blockState, direction, randomSource, modelData, renderType));
        return quads;
    }

    @Override
    public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
        return ChunkRenderTypeSet.of(RenderType.cutout());
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
}
