package jp.jsexpanded.server.animals.entity.extinct.terrestial;

import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.server.sound.JSExpandedSounds;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAnimalBase;
import jp.jurassicsaga.server.base.animal.entity.obj.info.AnimalGrowthStage;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.JSFloatTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.JSOpenDoorTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSCombatLeapTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSHerdCombatFollowTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSRevengeOrRunTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSTargetTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.metabolism.JSFindFoodTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.metabolism.JSFindWaterTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.misc.JSRestTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSFleeTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSRandomStrollTask;
import jp.jurassicsaga.server.v1.sound.JSV1Sounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import travelers.server.animal.entity.task.TravelerTaskController;

public class VenatosaurusEntity extends JSAnimalBase {

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
        controller.registerTask(new JSHerdCombatFollowTask(this).stalk());

        controller.registerTask(new JSRestTask(this));

        combatTargeting.registerTask(new JSTargetTask(this, true));
    }

    @Override
    protected int attackAnimLength() {
        return 20;
    }

    @Override
    public double jumpHeight() {
        return 1;
    }

    @Override
    protected @Nullable SoundEvent getAttackSound() {
        if(getTarget() != null) return JSExpandedSounds.VENATOSAURUS_THREAT.get();
        return JSExpandedSounds.VENATOSAURUS_ATTACK.get();
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return JSExpandedSounds.VENATOSAURUS_LIVING.get();
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
