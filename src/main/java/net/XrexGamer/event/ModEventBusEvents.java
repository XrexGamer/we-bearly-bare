package net.XrexGamer.event;

import net.XrexGamer.BearMod;
import net.XrexGamer.entity.ModdedMobs;
import net.XrexGamer.entity.client.GrizzModel;
import net.XrexGamer.entity.custom.GrizzlyBear;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;


@EventBusSubscriber(modid = BearMod.MOD_ID)

public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GrizzModel.LAYER_LOCATION, GrizzModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModdedMobs.GRIZZLY_BEAR.get(), GrizzlyBear.createAttributes().build());
    }








}
