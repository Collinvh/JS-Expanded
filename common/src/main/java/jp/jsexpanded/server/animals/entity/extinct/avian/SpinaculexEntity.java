package jp.jsexpanded.server.animals.entity.extinct.avian;

import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAvianBase;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.JSFloatTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSHerdCombatFollowTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSTargetTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSFleeTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSRandomStrollTask;
import jp.jurassicsaga.server.v1.sound.JSV1Sounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;
import travelers.server.animal.entity.pathingsystem.navigation.TravelersPathNavigation;
import travelers.server.animal.entity.task.TravelerTaskController;

public class SpinaculexEntity extends JSAvianBase {
    public SpinaculexEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveController = flyingMoveControl;
    }

    @Override
    protected void addTasksToController(TravelerTaskController controller, TravelerTaskController combatTargeting) {
        controller.registerTask(new JSFloatTask(this));
        controller.registerTask(new JSFleeTask(this, 10));
        controller.registerTask(new JSRandomStrollTask(this).setRange(6, 6));
        controller.registerTask(new JSHerdCombatFollowTask(this));
        combatTargeting.registerTask(new JSTargetTask(this, false));
    }

    public static boolean canSpawn(EntityType<? extends Entity> entityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return serverLevelAccessor.getBlockState(blockPos).is(Blocks.AIR) && serverLevelAccessor.getBrightness(LightLayer.SKY, blockPos) > 9;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        if(isFlying()) return JSV1Sounds.MOSQUITO_FLIGHT_LOOP.get();
        return null;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return JSV1Sounds.MOSQUITO_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return JSV1Sounds.MOSQUITO_HURT.get();
    }

    @Override
    public boolean isFlying() {
        return true;
    }

    @Override
    public boolean shouldDieInstantly() {
        return true;
    }

    @Override
    public boolean disableFlyTransitions() {
        return true;
    }

    @Override
    protected void tickDeath() {
        if (isDead() || shouldDieInstantly()) {
            if (shouldDieInstantly()) {
                if (getAnimal().getAnimalAttributes().getItemProperties().isHasDrops()) {
                    if (getAnimal().getAnimalAttributes().getItemProperties().isHasMeat() && random.nextFloat() < 0.6F) {
                        spawnAtLocation(new ItemStack(getAnimal().getItems().getRawMeat().get(), random.nextInt(4) + 1));
                    }
                    if (getAnimal().getAnimalAttributes().getMiscProperties().isExtinct() &&
                            getAnimal().getAnimalAttributes().getItemProperties().isHasFossil() && random.nextFloat() < 0.6F) {
                        spawnAtLocation(new ItemStack(getAnimal().getItems().getFossil_remains().get(), random.nextInt(4) + 1));
                    }
                }
            }
            if (!this.level().isClientSide() && !this.isRemoved()) {
                this.level().broadcastEntityEvent(this, (byte) 60);
                this.remove(Entity.RemovalReason.KILLED);
            }
            return;
        } else {
            super.tickDeath();
        }
    }

    @Override
    protected TravelersPathNavigation createNavigationController(Level level) {
        return getFlyingPathNavigation();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 40;
    }
}
