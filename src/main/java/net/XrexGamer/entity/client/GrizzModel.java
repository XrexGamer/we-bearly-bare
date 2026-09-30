package net.XrexGamer.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.XrexGamer.BearMod;
import net.XrexGamer.entity.custom.GrizzlyBear; // <-- adjust to wherever your real GrizzlyBear entity class actually lives
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;


    public class GrizzModel<T extends GrizzlyBear> extends HierarchicalModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION =
                new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BearMod.MOD_ID, "grizzly_bear"), "main");

        private final ModelPart root;
        private final ModelPart body;
        private final ModelPart tail;
        private final ModelPart neck;
        private final ModelPart head;
        private final ModelPart ear_left;
        private final ModelPart ear_right;
        private final ModelPart snout;
        private final ModelPart jaw;
        private final ModelPart legs;
        private final ModelPart front_left_leg;
        private final ModelPart front_right_leg;
        private final ModelPart back_left_leg;
        private final ModelPart back_right_leg;

        public GrizzModel(ModelPart root) {
            this.root = root.getChild("root");
            this.body = this.root.getChild("body");
            this.tail = this.body.getChild("tail");
            this.neck = this.body.getChild("neck");
            this.head = this.neck.getChild("head");
            this.ear_left = this.head.getChild("ear_left");
            this.ear_right = this.head.getChild("ear_right");
            this.snout = this.head.getChild("snout");
            this.jaw = this.head.getChild("jaw");
            this.legs = this.root.getChild("legs");
            this.front_left_leg = this.legs.getChild("front_left_leg");
            this.front_right_leg = this.legs.getChild("front_right_leg");
            this.back_left_leg = this.legs.getChild("back_left_leg");
            this.back_right_leg = this.legs.getChild("back_right_leg");
        }

        public static LayerDefinition createBodyLayer() {
            // unchanged — your cube geometry was correct, leaving exactly as-is
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(-1.0F, 12.0F, 19.5F));

            PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -9.0F, -17.0F, 18.0F, 18.0F, 15.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 65).addBox(-9.0F, -7.0F, -8.0F, 18.0F, 16.0F, 15.0F, new CubeDeformation(-0.002F))
                    .texOffs(0, 33).addBox(-9.0F, -8.0F, 3.0F, 18.0F, 17.0F, 15.0F, new CubeDeformation(0.0F))
                    .texOffs(92, 54).addBox(9.0F, -5.0F, 9.0F, 1.0F, 14.0F, 9.0F, new CubeDeformation(0.0F))
                    .texOffs(92, 77).addBox(-10.0F, -5.0F, 9.0F, 1.0F, 14.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, -13.5F));

            PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(36, 96).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 18.0F));

            PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(66, 18).addBox(-7.0F, -5.0F, -3.0F, 14.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -17.0F));

            PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(66, 0).addBox(-5.5F, -6.0F, -7.0F, 11.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -3.0F));

            PartDefinition ear_left = head.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(48, 96).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -6.0F, -5.0F));

            PartDefinition ear_right = head.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(100, 18).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -6.0F, -5.0F));

            PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(66, 94).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(20, 96).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 1.0F, -8.0F));

            PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 96).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -7.0F));

            PartDefinition legs = root.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(1.0F, 12.0F, -19.5F));

            PartDefinition front_left_leg = legs.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(66, 34).addBox(-3.0F, -1.0F, -3.5F, 6.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -12.0F, -6.5F));

            PartDefinition front_right_leg = legs.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(66, 54).addBox(-3.0F, -1.0F, -3.5F, 6.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -12.0F, -6.5F));

            PartDefinition back_left_leg = legs.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(92, 34).addBox(-3.0F, -1.0F, -3.5F, 6.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -12.0F, 19.5F));

            PartDefinition back_right_leg = legs.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(66, 74).addBox(-3.0F, -1.0F, -3.5F, 6.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -12.0F, 19.5F));

            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            this.root().getAllParts().forEach(ModelPart::resetPose);
            this.applyHeadRotation(netHeadYaw, headPitch);

            this.animateWalk(GrizzAnimations.anim_grizz_walk, limbSwing, limbSwingAmount, 2.0F, 2.5F);
            this.animate(entity.idleAnimationState, GrizzAnimations.anim_grizz_idle, ageInTicks, 1.0F);
            this.animate(entity.runAnimationState, GrizzAnimations.run, ageInTicks, 1.0F);
            this.animate(entity.attackAnimationState, GrizzAnimations.attack, ageInTicks, 1.0F);
        }

        private void applyHeadRotation(float headYaw, float headPitch) {
            headYaw = Mth.clamp(headYaw, -30f, 30f);
            headPitch = Mth.clamp(headPitch, -25f, 45f);

            this.head.yRot = headYaw * ((float) Math.PI / 180f);
            this.head.xRot = headPitch * ((float) Math.PI / 180f);
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
            root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        }

        @Override
        public ModelPart root() {
            return root;
        }
    }