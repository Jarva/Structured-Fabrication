package com.github.jarva.sf.client;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;

import java.util.function.Function;

public class FacadeGeometry implements IUnbakedGeometry<FacadeGeometry> {
    private final BlockModel base;

    public FacadeGeometry(BlockModel model) {
        this.base = model;
    }

    @Override
    public BakedModel bake(IGeometryBakingContext context, ModelBaker modelBaker, Function<Material, TextureAtlasSprite> function, ModelState modelState, ItemOverrides itemOverrides) {
        return new FacadeModel(this.base.bake(modelBaker, function, modelState));
    }

    @Override
    public void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter, IGeometryBakingContext context) {
        this.base.resolveParents(modelGetter);
    }

    public static class Loader implements IGeometryLoader<FacadeGeometry> {
        @Override
        public FacadeGeometry read(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            BlockModel model = jsonDeserializationContext.deserialize(jsonObject.get("model"), BlockModel.class);
            return new FacadeGeometry(model);
        }
    }
}
