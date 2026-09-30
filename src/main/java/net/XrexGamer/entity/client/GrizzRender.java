package net.XrexGamer.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.XrexGamer.BearMod;
import net.XrexGamer.entity.custom.GrizzlyBear;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;


public class GrizzRender extends MobRenderer<GrizzlyBear, GrizzModel<GrizzlyBear>> {
    public GrizzRender(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new GrizzModel<>(p_174304_.bakeLayer(GrizzModel.LAYER_LOCATION)), 1.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(GrizzlyBear p_114482_) {
        return ResourceLocation.fromNamespaceAndPath(BearMod.MOD_ID, "textures/entity/grizzly_bear/grizzy.png");
    }

    @Override
    public void render(GrizzlyBear p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {

        if (p_115308_.isBaby()) {
            p_115311_.scale(0.5f, 0.5f, 0.5f);
        } else {
            p_115311_.scale(1.2f, 1.2f, 1.2f);
        }

        super.render(p_115308_, p_115309_, p_115310_, p_115311_, p_115312_, p_115313_);
    }
}