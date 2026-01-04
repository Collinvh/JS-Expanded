package jp.jsexpanded.server.animals.entity.extinct.aquatic.cunaepraedator;

import jp.jsexpanded.server.animals.entity.extinct.JSExpandedModules;
import jp.jsexpanded.server.animals.entity.extinct.terrestial.venatosaurus.VenatosaurusTarget;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSCrablikeAnimal;
import jp.jurassicsaga.server.base.animal.entity.obj.nav.JSGroundNavigation;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.JSFloatTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.combat.JSHerdCombatFollowTask;
import jp.jurassicsaga.server.base.animal.entity.obj.tasks.navigation.JSRandomStrollTask;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import travelers.server.animal.entity.pathingsystem.navigation.TravelersPathNavigation;
import travelers.server.animal.entity.task.TravelerTaskController;

public class CunaepraedatorEntity extends JSCrablikeAnimal {
    public CunaepraedatorEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void createModuleHolder() {
        modules = new JSExpandedModules(this);
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
    protected boolean usesAir() {
        return false;
    }

    @Override
    protected void addTasksToController(TravelerTaskController controller, TravelerTaskController combatTargeting) {
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
        return 1.5F;
    }
}
