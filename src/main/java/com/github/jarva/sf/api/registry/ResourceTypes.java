package com.github.jarva.sf.api.registry;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.resources.ResourceType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class ResourceTypes {
    public static final ResourceKey<Registry<ResourceType<?>>> RESOURCE_TYPE_REGISTRY_KEY = ResourceKey.createRegistryKey(StructuredFabrication.prefix("resource_type"));
    public static final Registry<ResourceType<?>> RESOURCE_TYPE_REGISTRY = new RegistryBuilder<>(RESOURCE_TYPE_REGISTRY_KEY).create();
}
