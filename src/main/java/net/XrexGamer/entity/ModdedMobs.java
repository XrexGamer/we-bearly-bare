package net.XrexGamer.entity;

import net.XrexGamer.BearMod;
import net.XrexGamer.entity.custom.GrizzlyBear;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModdedMobs {
 public static final DeferredRegister<EntityType<?>> ENTITY_TYPE =
         DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, BearMod.MOD_ID );

 public static final Supplier<EntityType<GrizzlyBear>> GRIZZLY_BEAR =
         ENTITY_TYPE.register("grizzly_bear", () -> EntityType.Builder.of(GrizzlyBear::new, MobCategory.CREATURE)
                 .sized(1.4F, 1.3F).build("grizzly_bear"));


      public static void register (IEventBus eventBus) {
          ENTITY_TYPE.register(eventBus);
      }
}
