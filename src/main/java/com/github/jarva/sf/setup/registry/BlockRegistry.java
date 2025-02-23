package com.github.jarva.sf.setup.registry;


import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.common.block.ControllerBlock;
import com.github.jarva.sf.common.block.ResourceHatchBlock;
import com.github.jarva.sf.common.block.entity.ControllerEntity;
import com.github.jarva.sf.common.block.entity.ResourceHatchEntity;
import com.github.jarva.sf.common.item.ControllerBlockItem;
import com.github.jarva.sf.common.item.ResourceHatchBlockItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegistry {
    public static final List<DeferredBlock<? extends Block>> REGISTERED_BLOCKS = new ArrayList<>();

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(StructuredFabrication.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, StructuredFabrication.MODID);

    public static DeferredBlock<ControllerBlock> CONTROLLER_BLOCK = registerBlockAndItem("controller", ControllerBlock::new, (block) -> new ControllerBlockItem(block.get(), defaultItemProperties()));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ControllerEntity>> CONTROLLER_ENTITY = registerTile("controller", ControllerEntity::new, () -> new Block[]{CONTROLLER_BLOCK.get()});

    public static DeferredBlock<ResourceHatchBlock> RESOURCE_HATCH_BLOCK = registerBlockAndItem("resource_hatch", ResourceHatchBlock::new, (block) -> new ResourceHatchBlockItem(block.get(), defaultItemProperties()));
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ResourceHatchEntity>> RESOURCE_HATCH_ENTITY = registerTile("resource_hatch", ResourceHatchEntity::new, () -> new Block[]{RESOURCE_HATCH_BLOCK.get()});

    public static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Supplier<T> blockSupp) {
        return registerBlockAndItem(name, blockSupp, (block) -> getDefaultBlockItem(block.get()));
    }

    public static <T extends Block, R extends BlockItem> DeferredBlock<T> registerBlockAndItem(String name, Supplier<T> blockSupp, Function<DeferredBlock<T>, R> itemSupp) {
        DeferredBlock<T> block = BLOCKS.register(name, blockSupp);
        REGISTERED_BLOCKS.add(block);
        ItemRegistry.ITEMS.register(name, () -> itemSupp.apply(block));
        return block;
    }

    public static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> registerTile(String regName, BlockEntityType.BlockEntitySupplier<T> tile, Supplier<Block[]> block){
        return BLOCK_ENTITIES.register(regName, () -> BlockEntityType.Builder.of(tile, block.get()).build(null));
    }

    public static BlockItem getDefaultBlockItem(Block block) {
        return new BlockItem(block, defaultItemProperties());
    }

    public static Item.Properties defaultItemProperties() {
        return new Item.Properties();
    }
}
