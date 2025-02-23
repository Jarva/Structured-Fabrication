package com.github.jarva.sf.common.machines;

import com.github.jarva.sf.api.registry.Machines;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.HashMap;
import java.util.function.Function;

public record Machine(ResourceLocation id, String name, Either<ResourceLocation, StructureTemplate> structure) {
    public static final HashMap<ResourceLocation, StructureTemplate> TEMPLATES = new HashMap<ResourceLocation, StructureTemplate>();

    public static final Codec<Either<ResourceLocation, StructureTemplate>> TEMPLATE_CODEC = Codec.of(Machine::encodeTemplate, ResourceLocation.CODEC.map(Either::left));
    public static Codec<Machine> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(Machine::id),
            Codec.STRING.fieldOf("name").forGetter(Machine::name),
            TEMPLATE_CODEC.fieldOf("structure").forGetter(Machine::structure)
    ).apply(instance, Machine::new));
    public static final Codec<Holder<Machine>> CODEC = RegistryFileCodec.create(Machines.MACHINE_REGISTRY_KEY, DIRECT_CODEC);
    public static final Codec<HolderSet<Machine>> LIST_CODEC = RegistryCodecs.homogeneousList(Machines.MACHINE_REGISTRY_KEY, DIRECT_CODEC);

    public StructureTemplate getTemplate(StructureTemplateManager manager) {
        return structure.map(manager::getOrCreate, Function.identity());
    }

    public StructureInfo getInfo() {
        return new StructureInfo(TEMPLATES.get(this.id()));
    }

    public static void loadStructures(MinecraftServer server) {
        StructureTemplateManager manager = server.getStructureManager();

        server.registryAccess().registry(Machines.MACHINE_REGISTRY_KEY).ifPresent(registry -> {
            registry.holders().forEach(holder -> {
                Machine machine = holder.value();
                StructureTemplate template = machine.getTemplate(manager);
                TEMPLATES.put(machine.id(), template);
            });
        });
    }

    private static <T> DataResult<T> encodeTemplate(Either<ResourceLocation, StructureTemplate> template, DynamicOps<T> ops, T values) {
        return template.left().map(loc -> ResourceLocation.CODEC.encode(loc, ops, values)).orElse(DataResult.error(
                () -> "Cannot serialize a runtime structure template"
        ));
    }
}
