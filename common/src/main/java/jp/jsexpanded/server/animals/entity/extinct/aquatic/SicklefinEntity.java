package jp.jsexpanded.server.animals.entity.extinct.aquatic;

import jp.jurassicsaga.server.base.animal.entity.obj.bases.BuckitableAquaticBase;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAquaticBase;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSFleeTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSRandomStrollTask;
import jp.jurassicsaga.server.v1.animal.entity.obj.task.JSFlopTask;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import travelers.server.animal.entity.task.TravelerTaskController;

public class SicklefinEntity extends JSAquaticBase {
    public SicklefinEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void addTasksToController(TravelerTaskController controller, TravelerTaskController combatTargeting) {
        controller.registerTask(new JSFleeTask(this, 20));

        controller.registerTask(new JSRandomStrollTask(this).setRange(6, 6));
        controller.registerTask(new JSFlopTask(this));
    }

    @Override
    protected void tickDeath() {
        if (!this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(Entity.RemovalReason.KILLED);
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
