package com.github.jarva.sf.api.registry;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.resources.ResourceIngredient;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Function;

public class ResourceIngredients {
    public static final ResourceKey<Registry<MapCodec<? extends ResourceIngredient<?>>>> RESOURCE_INGREDIENT_REGISTRY_KEY = ResourceKey.createRegistryKey(StructuredFabrication.prefix("resource_ingredient"));
    public static final Registry<MapCodec<? extends ResourceIngredient<?>>> RESOURCE_INGREDIENT_REGISTRY = new RegistryBuilder<>(RESOURCE_INGREDIENT_REGISTRY_KEY).create();
    public static final Codec<ResourceIngredient<?>> RESOURCE_TYPE_DISPATCH = RESOURCE_INGREDIENT_REGISTRY.byNameCodec().dispatch(ResourceIngredient::codec, Function.identity());
}
