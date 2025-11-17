package jp.jsexpanded.server.animals.entity.extinct.terrestial.moonspider;

import jp.jsexpanded.server.animals.entity.extinct.terrestial.venatosaurus.VenatosaurusTarget;
import jp.jsexpanded.server.sound.JSExpandedSounds;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAnimalBase;
import jp.jurassicsaga.server.base.animal.entity.obj.nav.JSGroundNavigation;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.JSFloatTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.JSOpenDoorTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSHerdCombatFollowTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSRevengeOrRunTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSTargetTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.metabolism.JSFindFoodTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.metabolism.JSFindWaterTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.misc.JSRestTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSFleeTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSRandomStrollTask;
import jp.jurassicsaga.server.v1.animal.entity.extant.BasiliskEntity;
import jp.jurassicsaga.server.v1.animal.entity.extinct.terrestial.TroodonEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import travelers.server.animal.entity.pathingsystem.navigation.TravelersPathNavigation;
import travelers.server.animal.entity.task.TravelerTaskController;

public class MoonspiderEntity extends JSAnimalBase {
    private static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(MoonspiderEntity.class, EntityDataSerializers.BOOLEAN);

    public MoonspiderEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void tickDeath() {
        if (!this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public boolean shouldDieInstantly() {
        return true;
    }

    @Override
    protected void addTasksToController(TravelerTaskController controller, TravelerTaskController combatTargeting) {
        controller.registerTask(new JSFloatTask(this));
        controller.registerTask(new JSRandomStrollTask(this).setRange(6, 4));
        controller.registerTask(new JSHerdCombatFollowTask(this));

        combatTargeting.registerTask(new VenatosaurusTarget(this, true));
    }

    @Override
    public void attack(LivingEntity target) {
        super.attack(target);

        if (getTarget() != null) {
            var livingEntity = getTarget();

            livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 2, false, false));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 2, false, false));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 5, false, false));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 8, false, false));
        }
    }

    @Override
    protected TravelersPathNavigation createNavigationController(Level level) {
        var navigation = new JSGroundNavigation(this, level);
        navigation.setCanClimb(true);
        return navigation;
    }

    @Override
    protected int attackAnimLength() {
        return 20;
    }

    @Override
    public double jumpHeight() {
        return 3;
    }

    @Override
    public float maxUpStep() {
        return 0.5F;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    public void setClimbing(boolean climbing) {
        this.entityData.set(CLIMBING, climbing);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CLIMBING, false);
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return this.entityData.get(CLIMBING);
    }
}
