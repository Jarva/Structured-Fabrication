package com.github.jarva.sf.api.registry;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.common.machines.Machine;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class Machines {
    public static final ResourceKey<Registry<Machine>> MACHINE_REGISTRY_KEY = ResourceKey.createRegistryKey(StructuredFabrication.prefix("machines"));
}
