package com.github.jarva.sf.api.resources;

import com.mojang.serialization.MapCodec;

public interface ResourceIngredient<T extends ResourceStorage<?>> {
    MapCodec<? extends ResourceIngredient<T>> codec();

    default boolean isMatch(T hatch) {
        return false;
    }
}
