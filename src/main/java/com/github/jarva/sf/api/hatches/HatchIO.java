package com.github.jarva.sf.api.hatches;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.LanguageGeneration;
import com.google.common.base.Objects;
import com.mojang.serialization.Codec;

public class HatchIO implements LanguageGeneration {
    public static final HatchIO INPUT = new HatchIO("input", "Input");
    public static final HatchIO OUTPUT = new HatchIO("output", "Output");

    public static final Codec<HatchIO> CODEC = Codec.BOOL.xmap(bool -> bool ? INPUT : OUTPUT, io -> io.equals(INPUT));

    private final String name;
    private final String description;

    private HatchIO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getDescriptionId() {
        return "hatch_io." + StructuredFabrication.MODID + "." + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HatchIO hatchIO = (HatchIO) o;
        return Objects.equal(getName(), hatchIO.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}
