package jp.jsexpanded.server.animals.entity.extinct.aquatic;

import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAquaticBase;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSHerdCombatFollowTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSTargetTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSFleeTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSRandomStrollTask;
import jp.jurassicsaga.server.v1.animal.entity.obj.task.JSFlopTask;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import travelers.server.animal.entity.task.TravelerTaskController;

public class BloodfishEntity extends JSAquaticBase {
    public BloodfishEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void addTasksToController(TravelerTaskController controller, TravelerTaskController combatTargeting) {
        controller.registerTask(new JSFleeTask(this, 20));

        controller.registerTask(new JSRandomStrollTask(this).setRange(6, 6));
        controller.registerTask(new JSFlopTask(this));
        controller.registerTask(new JSHerdCombatFollowTask(this));

        combatTargeting.registerTask(new JSTargetTask(this, false));
    }

    @Override
    protected void tickDeath() {
        if (!this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public boolean shouldFlop() {
        return true;
    }

    @Override
    public float flopHeight() {
        return 0.15F;
    }

    @Override
    public boolean shouldDieInstantly() {
        return true;
    }
}
