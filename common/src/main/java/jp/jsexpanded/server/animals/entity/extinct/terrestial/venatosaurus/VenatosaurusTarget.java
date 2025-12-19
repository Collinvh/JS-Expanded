package jp.jsexpanded.server.animals.entity.extinct.terrestial.venatosaurus;

import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAnimalBase;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSTargetTask;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class VenatosaurusTarget extends JSTargetTask {
    public VenatosaurusTarget(JSAnimalBase baseAnimal, boolean mustSee) {
        super(baseAnimal, mustSee);
    }

    @Override
    protected void findTarget() {
        var tracking = this.getTargetSearchArea(this.animal.getTrackingRange());
        var entities = this.animal.level().getEntitiesOfClass(LivingEntity.class, tracking,
                mob -> {
                    if (mob.is(animal)) return false;
                    if (mustSee && !animal.hasLineOfSight(mob)) return false;
                    return animal.getModules().getMetabolismModule().canTarget(false, false, (Entity) mob);
                });

        LivingEntity nearest = this.animal.level().getNearestEntity(
                entities,
                TargetingConditions.DEFAULT.range(this.animal.getTrackingRange()),
                this.animal,
                this.animal.getX(),
                this.animal.getEyeY(),
                this.animal.getZ()
        );

        // if the nearest is a JSAnimalBase and herd is too big, flee instead
        if (nearest instanceof JSAnimalBase jsTarget) {
            var herdModule = jsTarget.getModules().getHerdModule();
            var meModule   = animal.getModules().getHerdModule();

            double theirStrength = herdModule.getHerdStrength()/10;
            double myStrength    = meModule.getHerdStrength()*2;

            if (myStrength < theirStrength) {
                this.target = null;
                this.animal.setFleeTarget(nearest);
                return;
            }
        }

        this.target = nearest;
    }
}
