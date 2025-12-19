package jp.jsexpanded.server.animals.entity.extinct.aquatic;

import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAmphibiousBase;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAquaticBase;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.JSFloatTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSHerdCombatFollowTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSTargetTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.misc.JSRestTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSFleeTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSRandomStrollTask;
import jp.jurassicsaga.server.v1.animal.entity.obj.task.JSFlopTask;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import travelers.server.animal.entity.task.TravelerTaskController;

public class PiranhadonEntity extends JSAmphibiousBase {
    public PiranhadonEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void addTasksToController(TravelerTaskController controller, TravelerTaskController combatTargeting) {
        controller.registerTask(new JSFleeTask(this, 20));
        controller.registerTask(new JSFloatTask(this));

        controller.registerTask(new JSRandomStrollTask(this).setRange(32, 6));
        controller.registerTask(new JSRestTask(this));
        controller.registerTask(new JSHerdCombatFollowTask(this));

        combatTargeting.registerTask(new JSTargetTask(this, false));
    }

    @Override
    protected boolean sinkToBottom() {
        return false;
    }

    @Override
    protected boolean usesAir() {
        return false;
    }
}
