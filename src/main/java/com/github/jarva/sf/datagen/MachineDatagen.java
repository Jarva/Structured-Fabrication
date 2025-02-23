package com.github.jarva.sf.datagen;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.registry.Machines;
import com.github.jarva.sf.common.machines.Machine;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MachineDatagen extends DatapackBuiltinEntriesProvider {
    public MachineDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, entries(), Set.of(StructuredFabrication.MODID));
    }

    public static final ResourceKey<Machine> EXAMPLE_MACHINE = ResourceKey.create(
            Machines.MACHINE_REGISTRY_KEY,
            StructuredFabrication.prefix("example")
    );

    public static RegistrySetBuilder entries() {
        return new RegistrySetBuilder()
            .add(Machines.MACHINE_REGISTRY_KEY, bootstrap -> {
                bootstrap.register(EXAMPLE_MACHINE, new Machine(EXAMPLE_MACHINE.location(), "Example", Either.left(StructuredFabrication.prefix("example"))));
            });
    }
}
