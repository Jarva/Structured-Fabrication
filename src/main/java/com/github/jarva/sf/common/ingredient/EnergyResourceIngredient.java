package com.github.jarva.sf.common.ingredient;

import com.github.jarva.sf.api.resources.ResourceIngredient;
import com.github.jarva.sf.common.type.EnergyResourceType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record EnergyResourceIngredient(int amount) implements ResourceIngredient<EnergyResourceType.Storage> {
    public static final MapCodec<EnergyResourceIngredient> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("amount").forGetter(EnergyResourceIngredient::amount)
    ).apply(instance, EnergyResourceIngredient::new));

    @Override
    public MapCodec<EnergyResourceIngredient> codec() {
        return CODEC;
    }

    @Override
    public boolean isMatch(EnergyResourceType.Storage hatch) {
        return hatch.getCapabilityHandler().getEnergyStored() >= amount;
    }
}
