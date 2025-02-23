package com.github.jarva.sf.common.ingredient;

import com.github.jarva.sf.api.resources.ResourceIngredient;
import com.github.jarva.sf.common.type.ItemResourceType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record ItemResourceIngredient(Ingredient ingredient) implements ResourceIngredient<ItemResourceType.Storage> {
    public static final MapCodec<ItemResourceIngredient> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("item").forGetter(ItemResourceIngredient::ingredient)
    ).apply(instance, ItemResourceIngredient::new));

    @Override
    public MapCodec<ItemResourceIngredient> codec() {
        return CODEC;
    }

    @Override
    public boolean isMatch(ItemResourceType.Storage hatch) {
        for (int i = 0; i < hatch.getCapabilityHandler().getSlots(); i++) {
            ItemStack stack = hatch.getCapabilityHandler().getStackInSlot(i);
            if (ingredient.test(stack)) {
                return true;
            }
        }
        return false;
    }
}
