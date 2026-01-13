package jp.jsexpanded.server.animals.extinct.avian;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.server.animals.AbstractAddonAnimal;
import jp.jsexpanded.server.animals.entity.extinct.avian.SpinaculexEntity;
import jp.jurassicsaga.JSCommon;
import jp.jurassicsaga.server.base.animal.animals.JSAnimations;
import jp.jurassicsaga.server.base.animal.animations.JSAnimator;
import jp.jurassicsaga.server.base.animal.obj.AbstractJSAnimal;
import jp.jurassicsaga.server.base.animal.obj.attributes.*;
import jp.jurassicsaga.server.base.generic.obj.EggType;
import jp.jurassicsaga.server.base.generic.obj.Era;
import jp.jurassicsaga.server.base.generic.util.JSUtils;
import jp.jurassicsaga.server.v1.animal.animations.extant.MosquitoAnimations;
import jp.jurassicsaga.server.v1.animal.entity.extant.MosquitoEntity;
import jp.jurassicsaga.server.v1.animal.entity.extinct.avian.MeganeuraEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import travelers.server.animal.entity.other.TravelersAnimalAnimationModule;
import travelers.server.animal.obj.TravelersMoveAnalysis;
import travelers.server.animal.obj.attributes.EntityAttributeProperties;
import travelers.server.animal.obj.attributes.EntityBaseProperties;
import travelers.server.animal.obj.locator.ResourceLocator;
import travelers.server.animal.obj.misc.AnimalSizeClass;
import travelers.server.animal.obj.misc.AnimalType;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static net.minecraft.world.entity.SpawnPlacementTypes.NO_RESTRICTIONS;
import static net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;

public class SpinaculexAnimal extends AbstractAddonAnimal<SpinaculexEntity> {
    public SpinaculexAnimal() {
        super("spinaculex");
        setAnimator(new Animations());
    }

    @Override
    protected void applyGeneticProperties(JSGeneticProperties<SpinaculexEntity> geneticProperties) {
        // no genetic properties
    }

    @Override
    protected void applyMetabolismProperties(JSMetabolismProperties<SpinaculexEntity> metabolismProperties) {
        metabolismProperties.disableSleep();
        metabolismProperties.disableBreeding();
        metabolismProperties.disableFood();
        metabolismProperties.disableWater();
        metabolismProperties.setMaxFood((int) JSUtils.toTicksMCDays(1));
    }

    @Override
    protected void applyMiscProperties(JSMiscProperties<SpinaculexEntity> miscProperties) {
        miscProperties.setGenderedVariants();
        miscProperties.setEra(Era.QUATERNARY);
        miscProperties.setExtinct();
        miscProperties.setVersion(JSExpanded.EXPANDED_VERSION);
        miscProperties.disableBabyGuidebook();
    }

    @Override
    protected void applySocialProperties(JSSocialGroupProperties<SpinaculexEntity> socialGroupProperties) {
        socialGroupProperties.addHuntTargets(Player.class, AbstractVillager.class);
        socialGroupProperties.addScaredOf(MeganeuraEntity.class);
        socialGroupProperties.addHerdTargets(MosquitoEntity.class);
        socialGroupProperties.setMaxDistanceToPackLeader(200);
        socialGroupProperties.setMinDistanceToPackLeader(50);
        socialGroupProperties.setMaxHerdSize(20);
    }

    @Override
    protected void applyItemProperties(JSItemProperties<SpinaculexEntity> itemProperties) {
        itemProperties.disableMeat();
        itemProperties.setHasFossil(false);
        itemProperties.setEggtype(EggType.SPIDER);
        itemProperties.setSpawnEggColors(0x9C1700, 0x3A0D00);
    }

    @Override
    protected void applyTravelersProperties(EntityAttributeProperties<SpinaculexEntity> attributes, EntityBaseProperties<SpinaculexEntity> base) {
        /*
        Base Properties
         */
        base.setAnimalType(AnimalType.INSECT);
        base.setSizeClass(AnimalSizeClass.SMALL);
        base.setMaxTurnRate(64);
        base.setTurnSmoothRate(0.8F);
        base.setMaxHeadRotation(90, 30);
        base.setRenderScale(0.212F);
        base.setLocator(new ResourceLocator<>() {
            private final Cache<String, ResourceLocation> cache = CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .build();

            @Override
            public ResourceLocation getModelLocation(SpinaculexEntity entity) {
                String name = entity.getAnimal().getAnimalAttributes().getAnimalName().toLowerCase(Locale.ROOT);
                String key = "model:" + name;
                try {
                    return cache.get(key, () ->
                            JSExpanded.createId("geo/animal/" + name + "/" + name + ".geo.json")
                    );
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public ResourceLocation getAnimationLocation(SpinaculexEntity entity) {
                String name = entity.getAnimal().getAnimalAttributes().getAnimalName().toLowerCase(Locale.ROOT);
                String key = "anim:" + name;
                try {
                    return cache.get(key, () ->
                            JSExpanded.createId("animations/animal/" + name + "/" + name + ".animation.json")
                    );
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public ResourceLocation getTextureLocation(SpinaculexEntity entity) {
                String name = entity.getAnimal().getAnimalAttributes().getAnimalName().toLowerCase(Locale.ROOT);
                String key = "tex:" + name;
                try {
                    return cache.get(key, () ->
                            JSExpanded.createId("textures/geo/animal/" + name + "/" + name + ".png")
                    );
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        /*
        Base Attributes
         */
        attributes.setSpawnFar();
        attributes.setEntityFactory(SpinaculexEntity::new);
        attributes.setCategory(MobCategory.AMBIENT);
        attributes.setEyeHeight(0.5F);
        attributes.setMaxHealth(0.5D);
        attributes.setAttackDamage(0.0F);
        attributes.setAttackSpeed(3F);
        attributes.setDimensions(0.5F, 0.5F);

        /*
        Speed Related
         */
        attributes.setMovementSpeed(0.04F);
        attributes.setRunningSpeedMultiplier(1.2F);
        attributes.setFlyingSpeed(0.15F);
    }

    private static class Animations extends JSAnimator<SpinaculexEntity> {
        @Override
        public void animate (SpinaculexEntity base, TravelersMoveAnalysis moveAnalysis, TravelersAnimalAnimationModule animationManager)
        {
            if (base.isFlying() && !base.onGround()) {
                JSAnimations.FLYING.sendForEntity(base);
            } else if (isMoving(moveAnalysis)) {
                JSAnimations.WALK.sendForEntity(base);
            } else {
                JSAnimations.IDLE.sendForEntity(base);
            }
        }
    }
}