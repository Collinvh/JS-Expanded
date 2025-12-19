package jp.jsexpanded.server.animals.entity.extinct.terrestial.venatosaurus;

import jp.jsexpanded.server.sound.JSExpandedSounds;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAnimalBase;
import jp.jurassicsaga.server.base.animal.entity.obj.info.AnimalGrowthStage;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.JSFloatTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.JSOpenDoorTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSHerdCombatFollowTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSRevengeOrRunTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.metabolism.JSFindFoodTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.metabolism.JSFindWaterTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.misc.JSRestTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSFleeTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSRandomStrollTask;
import jp.jurassicsaga.server.base.entity.obj.other.IJSLeapingEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import travelers.server.animal.entity.task.TravelerTaskController;

public class VenatosaurusEntity extends JSAnimalBase implements IJSLeapingEntity {
    private int minLeapTicks = 0;

    public VenatosaurusEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void addTasksToController(TravelerTaskController controller, TravelerTaskController combatTargeting) {
        controller.registerTask(new JSFloatTask(this));
        controller.registerTask(new JSOpenDoorTask(this));

        controller.registerTask(new JSFleeTask(this, 30));

        controller.registerTask(new JSRandomStrollTask(this).setRange(16, 9));
        controller.registerTask(new JSFindFoodTask(this));
        controller.registerTask(new JSFindWaterTask(this));
        controller.registerTask(new JSRevengeOrRunTask(this));
        controller.registerTask(new JSHerdCombatFollowTask(this).shouldCallout(true, 20));

        controller.registerTask(new JSRestTask(this));

        combatTargeting.registerTask(new VenatosaurusTarget(this, true));
    }

    @Override
    protected int attackAnimLength() {
        return 20;
    }

    @Override
    public double jumpHeight() {
        if (getModules().getGrowthStageModule().getGrowthStage() == AnimalGrowthStage.ADULT && !isInWater()) {
            return 6;
        } else {
            return 1;
        }
    }


    @Override
    public void onLeap() {
        if(getAttackSound() != null) {
            playSound(getAttackSound(), getSoundVolume(), 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
        }
        setLeaping(true);
        minLeapTicks = 1;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (isLeaping()) {
            if(minLeapTicks > 0) {
                minLeapTicks--;
            } else {
                if (onGround() || isInWater()) {
                    setLeaping(false);
                }
            }
        }
    }

    @Override
    protected @Nullable SoundEvent getAttackSound() {
        return JSExpandedSounds.VENATOSAURUS_ATTACK.get();
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return JSExpandedSounds.VENATOSAURUS_LIVING.get();
    }

    @Override
    protected @Nullable SoundEvent getCallSound() {
        return JSExpandedSounds.VENATOSAURUS_THREAT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return JSExpandedSounds.VENATOSAURUS_DEATH.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return JSExpandedSounds.VENATOSAURUS_HURT.get();
    }
}
