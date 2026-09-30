package net.XrexGamer.entity.custom;

import net.XrexGamer.entity.ModdedMobs;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Stack;

public class GrizzlyBear extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState runAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public int attackAnimationTimeout = 0;

    // Custom entity-event id used to tell the client "an attack swing just happened".
    private static final byte ATTACK_ANIMATION_EVENT_ID = 70;

    // Synced value: server sets this based on whether it has a target,
    // and the game automatically keeps the client's copy up to date.
    private static final EntityDataAccessor<Boolean> DATA_RUNNING =
            SynchedEntityData.defineId(GrizzlyBear.class, EntityDataSerializers.BOOLEAN);



    public GrizzlyBear(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_RUNNING, false);
    }

    public boolean isRunning() {
        return this.entityData.get(DATA_RUNNING);
    }

    private void setRunning(boolean running) {
        this.entityData.set(DATA_RUNNING, running);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector
                .addGoal(1, new PanicGoal(this, 2.0, p_350292_ -> p_350292_.isBaby() ? DamageTypeTags.PANIC_CAUSES : DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.5, false));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.targetSelector.addGoal(1, new GrizzlyBear.GrizzlyBearHurtByTargetGoal());
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }
    public static AttributeSupplier.Builder createAttributes() {
        return createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.FOLLOW_RANGE, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 12.0);
    }




    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(Items.CAKE);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModdedMobs.GRIZZLY_BEAR.get().create(serverLevel);
    }

    // Runs on the SERVER. Tells every nearby client "play the attack animation now"
    // via a broadcast event, instead of setting the animation state directly
    // (which the client would never see).
    @Override
    public boolean doHurtTarget(Entity target) {
        boolean result = super.doHurtTarget(target);
        if (result && !this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, ATTACK_ANIMATION_EVENT_ID);
        }
        return result;
    }

    // Runs on the CLIENT, triggered by the broadcast above. This is where the
    // animation state actually gets started, on the copy the model reads from.
    @Override
    public void handleEntityEvent(byte id) {
        if (id == ATTACK_ANIMATION_EVENT_ID) {
            this.attackAnimationTimeout = 25;
            this.attackAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    class GrizzlyBearHurtByTargetGoal extends HurtByTargetGoal {
        public GrizzlyBearHurtByTargetGoal() {
            super(GrizzlyBear.this);
        }

        @Override
        public void start() {
            super.start();
            if (GrizzlyBear.this.isBaby()) {
                this.alertOthers();
                this.stop();
            }
        }

        @Override
        protected void alertOther(Mob p_29580_, LivingEntity p_29581_) {
            if (p_29580_ instanceof GrizzlyBear && !p_29580_.isBaby()) {
                super.alertOther(p_29580_, p_29581_);
            }
        }
    }

    private void setupAnimationState() {
        boolean isMoving = this.getDeltaMovement().horizontalDistanceSqr() > 0.0001;

        if (!isMoving) {
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = 80;
                this.idleAnimationState.start(this.tickCount);
            } else {
                --this.idleAnimationTimeout;
            }
        } else {
            this.idleAnimationState.stop();
            this.idleAnimationTimeout = 0;
        }

        // Run while actively chasing a target (now reading the synced value,
        // not getTarget() directly, since getTarget() doesn't reach the client)
        if (this.isRunning()) {
            this.runAnimationState.startIfStopped(this.tickCount);
        } else {
            this.runAnimationState.stop();
        }

        if (this.attackAnimationTimeout > 0) {
            --this.attackAnimationTimeout;
        } else {
            this.attackAnimationState.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            this.setRunning(this.getTarget() != null);
        }

        if(this.level().isClientSide()) {
            this.setupAnimationState();
        }
    }
}