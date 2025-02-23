package com.github.jarva.sf.common.tier;

import com.github.jarva.sf.api.hatches.HatchTier;
import com.github.jarva.sf.common.config.EnergyHatchConfig;
import com.github.jarva.sf.common.config.FluidHatchConfig;
import com.github.jarva.sf.common.config.ItemHatchConfig;

public class CopperHatchTier extends HatchTier {
    public static HatchTier INSTANCE = new CopperHatchTier();

    public CopperHatchTier() {
        addConfig(new ItemHatchConfig(1));
        addConfig(new FluidHatchConfig(1000));
        addConfig(new EnergyHatchConfig(2048, 2048));
    }

    @Override
    public String getName() {
        return "copper";
    }

    @Override
    public String getDescription() {
        return "Copper";
    }
}
