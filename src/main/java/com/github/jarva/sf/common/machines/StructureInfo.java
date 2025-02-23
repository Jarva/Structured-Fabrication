package com.github.jarva.sf.common.machines;

import com.github.jarva.sf.StructuredFabrication;
import com.github.jarva.sf.common.block.ControllerBlock;
import com.github.jarva.sf.common.block.entity.ControllerEntity;
import com.github.jarva.sf.mixin.StructureTemplateAccessor;
import com.github.jarva.sf.setup.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.Palette;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StructureInfo {
    private final StructureTemplate template;
    private final StructureBlockInfo controller;
    private final Palette palette;
//    private final HashMap<BlockPos, ResourceHatchEntity> hatches = new HashMap<>();

    public StructureInfo(StructureTemplate template) {
        this.template = template;
        this.palette = getPalette(this.template);
        this.controller = findController(this.palette);
    }

    private Palette getPalette(StructureTemplate template) {
        StructureTemplateAccessor accessor = (StructureTemplateAccessor) template;
        if (accessor.getPalettes().isEmpty()) {
            return null;
        }

        return accessor.getPalettes().getFirst();
    }

    public StructureBlockInfo findController(Palette palette) {
        List<StructureBlockInfo> info = palette.blocks(BlockRegistry.CONTROLLER_BLOCK.get());

        if (info.isEmpty()) {
            StructuredFabrication.LOGGER.error("Unable to find controller block");
            return null;
        }

        if (info.size() > 1) {
            StructuredFabrication.LOGGER.warn("Expected 1 controller block, found {}", info.size());
        }

        return info.getFirst();
    }

    public Direction defaultFacing() {
        if (this.controller == null) return null;
        return this.controller.state().getValue(ControllerBlock.FACING);
    }

    public boolean validate(ServerLevel level, BlockPos origin, BlockState state) {
        BlockEntity originEntity = level.getBlockEntity(origin);
        if (!(originEntity instanceof ControllerEntity controllerEntity)) return false;

        Direction facing = state.getValue(ControllerBlock.FACING);
        Rotation rotation = getRotation(facing);

        List<StructureBlockInfo> blocks = transformTemplate(level, this.palette, rotation, origin);

        HashSet<BlockPos> hatches = new HashSet<>();

        for (StructureBlockInfo block : blocks) {
            BlockPos pos = block.pos();
            BlockState target = level.getBlockState(block.pos());

            BlockEntity blockEntity = level.getBlockEntity(pos);
//            if (blockEntity instanceof ResourceHatchEntity) {
//                hatches.add(pos);
//            }

            if (!block.state().equals(target)) {
                return false;
            }
        }

        for (BlockPos hatch : hatches) {
            if (!controllerEntity.addHatch(hatch)) return false;
        }

        return true;
    }

    public List<StructureBlockInfo> transformTemplate(ServerLevel level, Palette palette, Rotation rotation, BlockPos offset) {
        BlockPos origin = StructureTemplate.transform(this.controller.pos(), Mirror.NONE, rotation, BlockPos.ZERO);
        List<StructureBlockInfo> info = new ArrayList<>();
        for (StructureBlockInfo block : palette.blocks()) {
            BlockPos pos = StructureTemplate.transform(block.pos(), Mirror.NONE, rotation, BlockPos.ZERO).subtract(origin).offset(offset);
            info.add(new StructureBlockInfo(pos, block.state().rotate(level, pos, rotation), block.nbt()));
        }
        return info;
    }

    public Rotation getRotation(Direction facing) {
        Direction defaultFacing = defaultFacing();
        int difference = Math.abs(defaultFacing.get2DDataValue() - facing.get2DDataValue());
        return switch (difference) {
            case 1 -> Rotation.COUNTERCLOCKWISE_90;
            case 2 -> Rotation.CLOCKWISE_180;
            case 3 -> Rotation.CLOCKWISE_90;
            default -> Rotation.NONE;
        };
    }
}
