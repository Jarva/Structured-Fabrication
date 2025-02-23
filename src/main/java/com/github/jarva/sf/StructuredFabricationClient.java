package com.github.jarva.sf;

import com.github.jarva.sf.client.FacadeGeometry;
import com.github.jarva.sf.common.block.entity.AbstractFacadeEntity;
import com.github.jarva.sf.setup.registry.BlockRegistry;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = StructuredFabrication.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class StructuredFabricationClient {
    private static final List<ModelResourceLocation> MODEL_LOCATIONS = new ArrayList<>();
    private static final Map<ModelResourceLocation, BakedModel> MODELS = new HashMap<>();

    public static final ModelResourceLocation FACADE_OVERLAY = loc("block/facade_overlay");
    public static final ModelResourceLocation MACHINE_CASING = loc("block/machine_casing");

    @SubscribeEvent
    public static void modelLoader(ModelEvent.RegisterGeometryLoaders event) {
        event.register(StructuredFabrication.prefix("facade"), new FacadeGeometry.Loader());
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event) {
        for (ModelResourceLocation model : MODEL_LOCATIONS) {
            event.register(model);
        }
    }

    @SubscribeEvent
    public static void bakingModelsFinished(ModelEvent.BakingCompleted event) {
        for (ModelResourceLocation modelLocation : MODEL_LOCATIONS) {
            MODELS.put(modelLocation, event.getModels().get(modelLocation));
        }
    }

    @SubscribeEvent
    public static void initBlockColors(final RegisterColorHandlersEvent.Block event) {
        event.register((state, reader, pos, index) -> {
            if (reader != null && pos != null) {
                if (reader.getBlockEntity(pos) instanceof AbstractFacadeEntity facade) {
                    BlockState facadeState = facade.getFacadeState();
                    if (facadeState != null) {
                        return event.getBlockColors().getColor(facadeState, reader, pos, index);
                    }
                }
            }
            return -1;
        }, BlockRegistry.CONTROLLER_BLOCK.get());
    }

    private static ModelResourceLocation loc(String modelName) {
        ModelResourceLocation loc = ModelResourceLocation.standalone(StructuredFabrication.prefix(modelName));
        MODEL_LOCATIONS.add(loc);
        return loc;
    }

    public static BakedModel modelOf(ModelResourceLocation location) {
        return MODELS.get(location);
    }
}
