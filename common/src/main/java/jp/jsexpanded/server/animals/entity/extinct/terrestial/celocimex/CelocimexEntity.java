package jp.jsexpanded.server.animals.entity.extinct.terrestial.celocimex;

import jp.jsexpanded.server.animals.entity.extinct.terrestial.venatosaurus.VenatosaurusTarget;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAnimalBase;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAvianBase;
import jp.jurassicsaga.server.base.animal.entity.obj.info.AnimalGrowthStage;
import jp.jurassicsaga.server.base.animal.entity.obj.nav.JSAquaticNavigation;
import jp.jurassicsaga.server.base.animal.entity.obj.nav.JSGroundNavigation;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.JSFloatTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSCombatFollowTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSHerdCombatFollowTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSTargetTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.metabolism.JSFindFoodTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSFleeTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSRandomStrollTask;
import jp.jurassicsaga.server.v1.animal.entity.extinct.avian.MeganeuraEntity;
import jp.jurassicsaga.server.v1.animal.entity.obj.task.MeganeuraFloatTask;
import jp.jurassicsaga.server.v1.animal.entity.obj.task.MeganeuraFoodTask;
import jp.jurassicsaga.server.v1.sound.JSV1Sounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import travelers.server.animal.entity.pathingsystem.TravelersPath;
import travelers.server.animal.entity.pathingsystem.control.TravelersSmoothSwimMoveControl;
import travelers.server.animal.entity.pathingsystem.navigation.TravelersPathNavigation;
import travelers.server.animal.entity.pathingsystem.node.obj.TravelersPathType;
import travelers.server.animal.entity.task.TravelerTaskController;

public class CelocimexEntity extends JSAvianBase {
    public CelocimexEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void addTasksToController(TravelerTaskController controller, TravelerTaskController combatTargeting) {
        controller.registerTask(new JSFleeTask(this, 20));

        controller.registerTask(new JSFleeTask(this, 30));
        controller.registerTask(new JSRandomStrollTask(this));

        controller.registerTask(new JSFloatTask(this));
        controller.registerTask(new JSFindFoodTask(this));

        controller.registerTask(new JSCombatFollowTask(this));

        combatTargeting.registerTask(new JSTargetTask(this, false));
    }

    @Override
    public int getDistanceOfGround() {
        return 5;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        if(isFlying() && !isBaby()) return JSV1Sounds.MEGANEURA_FLIGHT_LOOP.get();
        return null;
    }

    @Override
    public boolean shouldSleep() {
        return false;
    }

    @Override
    protected boolean hoversInPlace() {
        return true;
    }
    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return JSV1Sounds.MEGANEURA_DEATH.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return JSV1Sounds.MEGANEURA_DEATH.get();
    }
}
