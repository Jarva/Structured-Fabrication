package com.github.jarva.sf.setup.registry.custom;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.resources.ResourceIngredient;
import com.github.jarva.sf.common.ingredient.EnergyResourceIngredient;
import com.github.jarva.sf.common.ingredient.FluidResourceIngredient;
import com.github.jarva.sf.common.ingredient.ItemResourceIngredient;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.github.jarva.sf.api.registry.ResourceIngredients.RESOURCE_INGREDIENT_REGISTRY;

public class ResourceIngredientRegistry {
    public static final DeferredRegister<MapCodec<? extends ResourceIngredient<?>>> RESOURCE_INGREDIENT = DeferredRegister.create(RESOURCE_INGREDIENT_REGISTRY, StructuredFabrication.MODID);

    public static final DeferredHolder<MapCodec<? extends ResourceIngredient<?>>, MapCodec<ItemResourceIngredient>> ITEM_RESOURCE = RESOURCE_INGREDIENT.register("item", () -> ItemResourceIngredient.CODEC);
    public static final DeferredHolder<MapCodec<? extends ResourceIngredient<?>>, MapCodec<FluidResourceIngredient>> FLUID_RESOURCE = RESOURCE_INGREDIENT.register("fluid", () -> FluidResourceIngredient.CODEC);
    public static final DeferredHolder<MapCodec<? extends ResourceIngredient<?>>, MapCodec<EnergyResourceIngredient>> ENERGY_RESOURCE = RESOURCE_INGREDIENT.register("energy", () -> EnergyResourceIngredient.CODEC);
}
