package com.github.jarva.sf.setup.registry;

import com.github.jarva.sf.StructuredFabrication;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemRegistry {
    public static final List<DeferredItem<?>> REGISTERED_ITEMS = new ArrayList<>();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(StructuredFabrication.MODID);

    public static <T extends Item> DeferredItem<T> register(String name, Supplier<T> item) {
        return register(name, item, true);
    }

    public static <T extends Item> DeferredItem<T> register(String name, Supplier<T> item, boolean dataGen) {
        DeferredItem<T> registered = ITEMS.register(name, item);
        REGISTERED_ITEMS.add(registered);
        return registered;
    }
}
