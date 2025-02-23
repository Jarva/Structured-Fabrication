package com.github.jarva.sf.datagen.client;

import com.github.jarva.sf.StructuredFabrication;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockStateDatagen extends BlockStateProvider {
    public BlockStateDatagen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, StructuredFabrication.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
//        simpleBlockWithItem(BlockRegistry.CONTROLLER_BLOCK.get());
    }

    private void simpleBlockWithItem(Block block) {
        simpleBlockWithItem(block, cubeAll(block));
    }

    private void simpleBlockItem(Block block) {
        simpleBlockItem(block, cubeAll(block));
    }
}
