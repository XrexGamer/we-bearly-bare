package net.XrexGamer.datagen;

import net.XrexGamer.BearMod;
import net.XrexGamer.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BearMod.MOD_ID , existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ModItems.GRIZZLY_BEAR_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }
}
