package com.github.jarva.sf.datagen;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.api.LanguageGeneration;
import com.github.jarva.sf.api.hatches.HatchIO;
import com.github.jarva.sf.api.registry.HatchTiers;
import com.github.jarva.sf.api.registry.ResourceTypes;
import com.github.jarva.sf.setup.registry.BlockRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LangDatagen extends LanguageProvider {
    public LangDatagen(PackOutput output, String locale) {
        super(output, StructuredFabrication.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("label.sf.base", "Base %s");

        String controller = BlockRegistry.CONTROLLER_BLOCK.get().getDescriptionId();
        add(controller, "Controller");

        String hatch = BlockRegistry.RESOURCE_HATCH_BLOCK.get().getDescriptionId();
        add(hatch, "Resource Hatch");
        add(hatch + ".bound", "%s %s %s Hatch");

        HatchTiers.HATCH_TIER_REGISTRY.stream().forEach(this::addTranslation);
        ResourceTypes.RESOURCE_TYPE_REGISTRY.stream().forEach(this::addTranslation);

        addTranslation(HatchIO.INPUT);
        addTranslation(HatchIO.OUTPUT);
    }

    public void addTranslation(LanguageGeneration item) {
        add(item.getDescriptionId(), item.getDescription());
    }
}
