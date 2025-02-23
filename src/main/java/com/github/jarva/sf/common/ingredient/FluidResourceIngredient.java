package com.github.jarva.sf.common.ingredient;

import com.github.jarva.sf.api.resources.ResourceIngredient;
import com.github.jarva.sf.common.type.FluidResourceType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

public record FluidResourceIngredient(FluidIngredient ingredient) implements ResourceIngredient<FluidResourceType.Storage> {
    public static final MapCodec<FluidResourceIngredient> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            FluidIngredient.CODEC.fieldOf("fluid").forGetter(FluidResourceIngredient::ingredient)
    ).apply(instance, FluidResourceIngredient::new));

    @Override
    public MapCodec<FluidResourceIngredient> codec() {
        return CODEC;
    }

    @Override
    public boolean isMatch(FluidResourceType.Storage hatch) {
        return ingredient.test(hatch.getCapabilityHandler().getFluid());
    }
}
