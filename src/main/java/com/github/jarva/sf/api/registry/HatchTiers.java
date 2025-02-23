package com.github.jarva.sf.api.registry;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.hatches.HatchTier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class HatchTiers {
    public static final ResourceKey<Registry<HatchTier>> HATCH_TIER_REGISTRY_KEY = ResourceKey.createRegistryKey(StructuredFabrication.prefix("hatch_tier"));
    public static final Registry<HatchTier> HATCH_TIER_REGISTRY = new RegistryBuilder<>(HATCH_TIER_REGISTRY_KEY).create();
}
