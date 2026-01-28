package jp.jsexpanded.server.animals.extinct.avian;

import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.server.animals.AbstractAddonAnimal;
import jp.jsexpanded.server.animals.entity.extinct.avian.celocimex.CelocimexEntity;
import jp.jsexpanded.server.animals.entity.extinct.terrestial.moonspider.MoonspiderEntity;
import jp.jurassicsaga.server.base.animal.animals.JSAnimations;
import jp.jurassicsaga.server.base.animal.animations.JSAnimator;
import jp.jurassicsaga.server.base.animal.entity.obj.diet.Diets;
import jp.jurassicsaga.server.base.animal.entity.obj.info.AnimalDietType;
import jp.jurassicsaga.server.base.animal.obj.attributes.*;
import jp.jurassicsaga.server.base.generic.obj.EggType;
import jp.jurassicsaga.server.base.generic.obj.Era;
import jp.jurassicsaga.server.base.generic.util.JSUtils;
import jp.jurassicsaga.server.v1.animal.animals.extinct.avian.MeganeuraAnimal;
import jp.jurassicsaga.server.v1.animal.entity.extant.GoatEntity;
import jp.jurassicsaga.server.v1.animal.entity.extant.MosquitoEntity;
import jp.jurassicsaga.server.v1.animal.entity.extinct.avian.MeganeuraEntity;
import jp.jurassicsaga.server.v1.animal.entity.extinct.terrestial.*;
import jp.jurassicsaga.server.v2.animal.entity.extant.avian.ButterflyEntity;
import jp.jurassicsaga.server.v2.animal.entity.extinct.terrestial.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import travelers.server.animal.entity.other.TravelersAnimalAnimationModule;
import travelers.server.animal.obj.TravelersMoveAnalysis;
import travelers.server.animal.obj.attributes.EntityAttributeProperties;
import travelers.server.animal.obj.attributes.EntityBaseProperties;
import travelers.server.animal.obj.locator.ResourceLocator;
import travelers.server.animal.obj.misc.AnimalType;

public class CelocimexAnimal extends AbstractAddonAnimal<CelocimexEntity> {
    public CelocimexAnimal() {
        super("celocimex");
        setAnimator(new Animations());
    }

    @Override
    protected void applyGeneticProperties(JSGeneticProperties<CelocimexEntity> geneticProperties) {
        geneticProperties.addGrowthStageScaling(0.1F);
        geneticProperties.addGrowthNextStageScaling(0.5F);
        geneticProperties.addGrowthStageSize(0.5f, 0.4f);
        geneticProperties.addGrowthStageMaxGrowth((int) JSUtils.toTicksMCDays(4.9F));
        geneticProperties.setGrowthProgressCap((int) JSUtils.toTicksMCDays(5));
        geneticProperties.addSupportedGenes();
    }

    @Override
    protected void applyMetabolismProperties(JSMetabolismProperties<CelocimexEntity> metabolismProperties) {
        metabolismProperties.setDiet(AnimalDietType.INSECTOVORE, () ->
                Diets.INSECTOVORE.get().addItem(net.minecraft.world.item.Items.CHICKEN)
        );
        metabolismProperties.disableWater();
        metabolismProperties.disableFood();
    }

    @Override
    protected void applyMiscProperties(JSMiscProperties<CelocimexEntity> miscProperties) {
        miscProperties.setGuidebookScaling(new float[]{3, 7});
        miscProperties.setGuidebookOffset(new Vec2(-0.45F, -0), new Vec2(0, -0.08F));
        miscProperties.setCorpseMeatDrops(1, 1);
        miscProperties.setCorpseDropTimes(1);
        miscProperties.setCorpseBoneDrops(0, 1);
        miscProperties.setEra(Era.QUATERNARY);
        miscProperties.setMaxHeadRotation(25, 30);
        miscProperties.setBabyAnimations();
        miscProperties.setExtinct();

        miscProperties.setGuideBookDescription("Celocimex is a species of dragonfly native to the jungle canopies of Skull Island. Little is known of Celocimex, but it is assumed to fill the same niche as mainland dragonflies as a pursuit micropredator.");
        miscProperties.setGuideBookSource("Skull Island");
        miscProperties.setGuideBookScientificName("Celocimex");

        miscProperties.setAdvancementTitle("Converse your Ammunition!");
        miscProperties.disableBabyGuidebook();
        miscProperties.setVersion(JSExpanded.EXPANDED_VERSION);
    }

    @Override
    protected void applySocialProperties(JSSocialGroupProperties<CelocimexEntity> socialGroupProperties) {
        socialGroupProperties.addScaredOf(
                //V1
                VelociraptorEntity.class, TroodonEntity.class, DilophosaurusEntity.class,
                TyrannosaurusEntity.class, MeganeuraEntity.class,

                //V2
                AchillobatorEntity.class, BaryonyxEntity.class, MetriacanthosaurusEntity.class
        );
        socialGroupProperties.addHuntTargets(MosquitoEntity.class, ButterflyEntity.class);
        socialGroupProperties.addHerdTargets(CelocimexEntity.class);
        socialGroupProperties.setMaxDistanceToPackLeader(900);
        socialGroupProperties.setMinDistanceToPackLeader(400);
        socialGroupProperties.setMaxHerdSize(7);
        socialGroupProperties.setNaturalAggression(1F);
    }

    @Override
    protected void applyItemProperties(JSItemProperties<CelocimexEntity> itemProperties) {
        itemProperties.setEggtype(EggType.SPIDER);
        itemProperties.setSpawnEggColors(0x313724, 0x615030);
        itemProperties.setHasFossil(false);
    }

    @Override
    protected void applyTravelersProperties(EntityAttributeProperties<CelocimexEntity> attributes, EntityBaseProperties<CelocimexEntity> base) {
        /*
        Base Properties
         */
        base.setAnimalType(AnimalType.INSECT);
        base.setFov(90);
        base.setMaxTurnRate(9);
        base.setSizeDimorphism(9);
        base.setTurnSmoothRate(0.7F);

        /*
        Render Properties
         */
        base.setRenderScale(0.225F);
        base.setLocator(new ResourceLocator<>() {
            private static final ResourceLocation texture = JSExpanded.createId("textures/geo/animal/celocimex/celocimex.png");
            private static final ResourceLocation model = JSExpanded.createId("geo/animal/celocimex/celocimex_adult.geo.json");
            private static final ResourceLocation animation = JSExpanded.createId("animations/animal/celocimex/celocimex.animation.json");

            @Override
            public ResourceLocation getTextureLocation(CelocimexEntity entity) {
                return texture;
            }

            @Override
            public ResourceLocation getModelLocation(CelocimexEntity entity) {
                return model;
            }

            @Override
            public ResourceLocation getAnimationLocation(CelocimexEntity entity) {
                return animation;
            }
        });

        /*
        Base Attributes
         */
        attributes.setPersistent();
        attributes.setWaterEfficiency(0.6F);
        attributes.setEntityFactory(CelocimexEntity::new);
        attributes.setEyeHeight(0.95F);
        attributes.setCategory(MobCategory.CREATURE);
        attributes.setMaxHealth(15.0F);
        attributes.setDimensions(0.5F,0.5F);
        attributes.setTrackingRange(64);

        /*
        Speed Related
         */
        attributes.setMovementSpeed(JSUtils.kmhToSpeed(16));
        attributes.setWaterEfficiency(0.8F);
        attributes.setRunningSpeedMultiplier(2.7F);

        /*
        Combat Related
         */
        attributes.setAttackDamage(6F);
        attributes.setAttackSpeed(0.2F);
        attributes.setFlyingSpeed(0.2F);
        attributes.setAttackKnockback(0.5F);
        attributes.setFollowRange(64.0F);
    }

    static class Animations extends JSAnimator<CelocimexEntity> {
        @Override
        public void animate(CelocimexEntity base, TravelersMoveAnalysis moveAnalysis, TravelersAnimalAnimationModule animationManager) {
            if (base.isDead()) {
                JSAnimations.DEATH.sendForEntity(base);
                return;
            }

            if (base.isInWater()) {
                JSAnimations.DROWN.sendForEntity(base);
                return;
            }

            if (base.curAttackTicks > 0) {
                JSAnimations.ATTACK.sendForEntity(base);
                return;
            }

            if (base.isFlying() && !base.onGround()) {
                if (isMoving(moveAnalysis)) {
                    JSAnimations.FLYING.sendForEntity(base);
                } else {
                    JSAnimations.HOVER.sendForEntity(base);
                }
            } else {
                if (base.onGround()) {
                    if (isMoving(moveAnalysis)) {
                        JSAnimations.FLYING.sendForEntity(base);
                    } else {
                        JSAnimations.IDLE.sendForEntity(base);
                    }
                } else {
                    JSAnimations.HOVER.sendForEntity(base);
                }
            }
        }
    }
}
