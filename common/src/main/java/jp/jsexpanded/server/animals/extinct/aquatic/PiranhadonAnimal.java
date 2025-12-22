package jp.jsexpanded.server.animals.extinct.aquatic;

import jp.jsexpanded.server.animals.AbstractAddonAnimal;
import jp.jsexpanded.server.animals.JSExpandedLocator;
import jp.jsexpanded.server.animals.entity.extinct.aquatic.BloodfishEntity;
import jp.jsexpanded.server.animals.entity.extinct.aquatic.PiranhadonEntity;
import jp.jsexpanded.server.animals.entity.extinct.terrestial.moonspider.MoonspiderEntity;
import jp.jurassicsaga.server.base.animal.animals.JSAnimations;
import jp.jurassicsaga.server.base.animal.animations.JSAnimator;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAnimalBase;
import jp.jurassicsaga.server.base.animal.entity.obj.diet.Diets;
import jp.jurassicsaga.server.base.animal.entity.obj.info.AnimalDietType;
import jp.jurassicsaga.server.base.animal.obj.attributes.*;
import jp.jurassicsaga.server.base.generic.obj.EggType;
import jp.jurassicsaga.server.base.generic.obj.Era;
import jp.jurassicsaga.server.base.generic.util.JSUtils;
import jp.jurassicsaga.server.v1.animal.entity.extant.BonitoEntity;
import jp.jurassicsaga.server.v1.animal.entity.extant.GoatEntity;
import jp.jurassicsaga.server.v1.animal.entity.extant.OstrichEntity;
import jp.jurassicsaga.server.v1.animal.entity.extinct.aquatic.MesolimulusEntity;
import jp.jurassicsaga.server.v1.animal.entity.extinct.aquatic.TylosaurusEntity;
import jp.jurassicsaga.server.v1.animal.entity.extinct.terrestial.*;
import jp.jurassicsaga.server.v2.animal.entity.extinct.terrestial.*;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec2;
import travelers.server.animal.entity.other.TravelersAnimalAnimationModule;
import travelers.server.animal.obj.TravelersMoveAnalysis;
import travelers.server.animal.obj.attributes.EntityAttributeProperties;
import travelers.server.animal.obj.attributes.EntityBaseProperties;
import travelers.server.animal.obj.misc.AnimalType;

public class PiranhadonAnimal extends AbstractAddonAnimal<PiranhadonEntity> {
    public PiranhadonAnimal() {
        super("piranhadon");
        setAnimator(new Animations());
    }

    @Override
    protected void applyGeneticProperties(JSGeneticProperties<PiranhadonEntity> geneticProperties) {
        geneticProperties.addGrowthStageScaling(0.4F);
        geneticProperties.addGrowthStageSize(0.5f, 0.7f);
        geneticProperties.addGrowthNextStageScaling(1.1F);
        geneticProperties.addGrowthStageMaxGrowth((int) JSUtils.toTicksMCDays(4));
        geneticProperties.setGrowthProgressCap((int) JSUtils.toTicksMCDays(6));
        geneticProperties.addSupportedGenes();
    }

    @Override
    protected void applyMetabolismProperties(JSMetabolismProperties<PiranhadonEntity> metabolismProperties) {
        metabolismProperties.setDiet(AnimalDietType.CARNIVORE, Diets.CARNIVORE);
        metabolismProperties.setMaxWater((int) JSUtils.toTicksMCDays(2F));
        metabolismProperties.setMaxFood((int) JSUtils.toTicksMCDays(1F));
        metabolismProperties.setStalkingSpeedMultiplier(0.7F);
    }

    @Override
    protected void applyMiscProperties(JSMiscProperties<PiranhadonEntity> miscProperties) {
        miscProperties.setEra(Era.QUATERNARY);
        miscProperties.setMaxHeadRotation(20, 46);
        miscProperties.setGuidebookScaling(new float[]{ 1.2f, 0.6f });
        miscProperties.setGuidebookOffset(new Vec2(-1,0),new Vec2(0,0));
        miscProperties.setExtinct();

        miscProperties.setGuideBookDescription("Piranhadon was a massive carnivorous river-dwelling fish that inhabited the swamps of Skull Island.As an ambush predator, Piranhadon would lie in wait near the river banks, using its sensitive barbels to detect prey.");
        miscProperties.setGuideBookSource("Skull Island");
        miscProperties.setGuideBookScientificName("Piranhadon");

        miscProperties.setAdvancementTitle("Boom piranha");
        miscProperties.disableBabyGuidebook();
        miscProperties.setVersion(-1);
    }

    @Override
    protected void applySocialProperties(JSSocialGroupProperties<PiranhadonEntity> socialGroupProperties) {
        socialGroupProperties.addHuntTargets(
                Player.class, MesolimulusEntity.class, AbstractFish.class,
                Animal.class, GoatEntity.class, OstrichEntity.class,
                GallimimusEntity.class, DryosaurusEntity.class, BonitoEntity.class,
                DilophosaurusEntity.class, HadrosaurusEntity.class, ParasaurolophusEntity.class,


                //V2
                MaiasauraEntity.class, MicroceratusEntity.class, StyracosaurusEntity.class,
                ProceratosaurusEntity.class, OthnielaEntity.class, EuoplocephalusEntity.class,
                VelociraptorEntity.class, AchillobatorEntity.class, CoelurusEntity.class,
                CallovosaurusEntity.class, ProtoceratopsEntity.class, TylosaurusEntity.class
        );
        socialGroupProperties.addHerdTargets(PiranhadonEntity.class);
        socialGroupProperties.setMaxDistanceToPackLeader(900);
        socialGroupProperties.setMinDistanceToPackLeader(400);
        socialGroupProperties.setMaxHerdSize(2);
        socialGroupProperties.setNaturalAggression(1F);
    }

    @Override
    protected void applyItemProperties(JSItemProperties<PiranhadonEntity> itemProperties) {
        itemProperties.setEggtype(EggType.FISH);
        itemProperties.setSpawnEggColors(0x958C5F, 0x313525);
        itemProperties.setSpawnEggColorsMale(0x6E6745, 0x181A10);
        itemProperties.disableMeat();
        itemProperties.setHasFossil(false);
    }

    @Override
    protected void applyTravelersProperties(EntityAttributeProperties<PiranhadonEntity> attributes, EntityBaseProperties<PiranhadonEntity> base) {
        /*
        Base Properties
         */
        base.setAnimalType(AnimalType.FISH);
        base.setFov(90);
        base.setMaxTurnRate(9);
        base.setSizeDimorphism(9);
        base.setTurnSmoothRate(0.7F);

        /*
        Render Properties
         */
        base.setRenderScale(1.9F);
        base.setRenderScaleMale(0.9F);
        base.setLocator(new JSExpandedLocator<PiranhadonEntity>().setAdultOnly());

        /*
        Base Attributes
         */
        attributes.setPersistent();
        attributes.setWaterEfficiency(0.6F);
        attributes.setEntityFactory(PiranhadonEntity::new);
        attributes.setEyeHeight(0.95F);
        attributes.setCategory(MobCategory.CREATURE);
        attributes.setMaxHealth(90.0F);
        attributes.setDimensions(2.5F,2.5F);
        attributes.setTrackingRange(64);

        /*
        Speed Related
         */
        attributes.setMovementSpeed(JSUtils.kmhToSpeed(10));
        attributes.setWaterEfficiency(0.8F);
        attributes.setRunningSpeedMultiplier(1.45F);
        attributes.setSwimmingSpeedMultiplier(2F);

        /*
        Combat Related
         */
        attributes.setAttackDamage(24F);
        attributes.setAttackSpeed(2F);
        attributes.setAttackKnockback(0.5F);
        attributes.setFollowRange(64.0F);
    }

    static class Animations extends JSAnimator<PiranhadonEntity> {
        @Override
        public void animate(PiranhadonEntity base, TravelersMoveAnalysis moveAnalysis, TravelersAnimalAnimationModule animationManager) {
            if(base.isDead()) {
                if(base.isInWater()) {
                    JSAnimations.DEATH.sendForEntity(base);
                } else {
                    JSAnimations.ON_LAND_DEATH.sendForEntity(base);
                }
                return;
            }

            if (base.isInWater()) {
                if (isMoving(moveAnalysis)) {
                    JSAnimations.SWIM.sendForEntity(base);
                } else {
                    JSAnimations.IDLE_IN_WATER.sendForEntity(base);
                }
            } else {
                if (isMoving(moveAnalysis)) {
                    JSAnimations.WALK_BEACHED.sendForEntity(base);
                } else {
                    JSAnimations.BEACHED.sendForEntity(base);
                }
            }
        }

        @Override
        public void animateServer(PiranhadonEntity base, TravelersMoveAnalysis moveAnalysis, TravelersAnimalAnimationModule animationManager) {
            if(base.isDead()) return;

            if(animationManager.playTransition(base.isResting(), true,  JSAnimations.REST_IN.wrap(36), JSAnimations.REST_LOOP.wrap(), JSAnimations.REST_OUT.wrap(19))) {
                return;
            }

            if (base.curAttackTicks > 0) {
                JSAnimations.ATTACK.sendForEntity(base);
                return;
            }
            if (base.curInjuredTicks > 0) {
                if(base.isInWater()) {
                    JSAnimations.ON_LAND_INJURED.sendForEntity(base);
                } else {
                    JSAnimations.INJURED.sendForEntity(base);
                }
                return;
            }
        }
    }
}

