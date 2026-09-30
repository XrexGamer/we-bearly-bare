package net.XrexGamer.item;

import net.XrexGamer.BearMod;
import net.XrexGamer.entity.ModdedMobs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BearMod.MOD_ID);


    public static final DeferredItem<Item> GRIZZLY_BEAR_SPAWN_EGG = ITEMS.register("grizzly_bear_spawn_egg",
            () -> new DeferredSpawnEggItem(ModdedMobs.GRIZZLY_BEAR, 0x361201, 0x8f3106, new Item.Properties()));








    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
