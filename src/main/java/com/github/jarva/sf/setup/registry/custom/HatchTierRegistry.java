package com.github.jarva.sf.setup.registry.custom;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.hatches.HatchTier;
import com.github.jarva.sf.common.tier.CopperHatchTier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.github.jarva.sf.api.registry.HatchTiers.HATCH_TIER_REGISTRY;

public class HatchTierRegistry {
    public static final DeferredRegister<HatchTier> HATCH_TIER = DeferredRegister.create(HATCH_TIER_REGISTRY, StructuredFabrication.MODID);

    public static final DeferredHolder<HatchTier, HatchTier> COPPER_TIER = HATCH_TIER.register("copper", () -> CopperHatchTier.INSTANCE);
//    public static final DeferredHolder<HatchTier, HatchTier> IRON_TIER = HATCH_TIER.register("iron", () -> CopperHatchTier.INSTANCE);
//    public static final DeferredHolder<HatchTier, HatchTier> GOLD_TIER = HATCH_TIER.register("gold", () -> CopperHatchTier.INSTANCE);
//    public static final DeferredHolder<HatchTier, HatchTier> DIAMOND_TIER = HATCH_TIER.register("diamond", () -> CopperHatchTier.INSTANCE);
//    public static final DeferredHolder<HatchTier, HatchTier> NETHERITE_TIER = HATCH_TIER.register("netherite", () -> CopperHatchTier.INSTANCE);
}
