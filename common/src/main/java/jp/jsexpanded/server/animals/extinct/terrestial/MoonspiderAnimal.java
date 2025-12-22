package jp.jsexpanded.server.animals.extinct.terrestial;

import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.server.animals.AbstractAddonAnimal;
import jp.jsexpanded.server.animals.JSExpandedLocator;
import jp.jsexpanded.server.animals.entity.extinct.aquatic.SicklefinEntity;
import jp.jsexpanded.server.animals.entity.extinct.terrestial.moonspider.MoonspiderEntity;
import jp.jsexpanded.server.animals.entity.extinct.terrestial.venatosaurus.VenatosaurusEntity;
import jp.jurassicsaga.server.base.animal.animals.JSAnimations;
import jp.jurassicsaga.server.base.animal.animations.JSAnimator;
import jp.jurassicsaga.server.base.animal.entity.obj.bases.JSAnimalBase;
import jp.jurassicsaga.server.base.animal.entity.obj.diet.Diets;
import jp.jurassicsaga.server.base.animal.entity.obj.info.AnimalDietType;
import jp.jurassicsaga.server.base.animal.obj.attributes.*;
import jp.jurassicsaga.server.base.generic.obj.ActiveTime;
import jp.jurassicsaga.server.base.generic.obj.EggType;
import jp.jurassicsaga.server.base.generic.obj.Era;
import jp.jurassicsaga.server.base.generic.util.JSUtils;
import jp.jurassicsaga.server.v1.animal.animations.extinct.terrestial.VelociraptorAnimations;
import jp.jurassicsaga.server.v1.animal.entity.extant.GoatEntity;
import jp.jurassicsaga.server.v1.animal.entity.extant.OstrichEntity;
import jp.jurassicsaga.server.v1.animal.entity.extinct.terrestial.*;
import jp.jurassicsaga.server.v2.animal.entity.extinct.terrestial.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
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
import travelers.server.animal.obj.locator.ResourceLocator;
import travelers.server.animal.obj.misc.AnimalType;

public class MoonspiderAnimal extends AbstractAddonAnimal<MoonspiderEntity> {
    public MoonspiderAnimal() {
        super("moonspider");
        setAnimator(new Animations());
    }

    @Override
    protected void applyGeneticProperties(JSGeneticProperties<MoonspiderEntity> geneticProperties) {
        geneticProperties.addGrowthStageScaling(0.4F);
        geneticProperties.addGrowthStageSize(0.5f, 0.7f);
        geneticProperties.addGrowthNextStageScaling(1.1F);
        geneticProperties.addGrowthStageMaxGrowth((int) JSUtils.toTicksMCDays(4));
        geneticProperties.setGrowthProgressCap((int) JSUtils.toTicksMCDays(6));
        geneticProperties.addSupportedGenes();
    }

    @Override
    protected void applyMetabolismProperties(JSMetabolismProperties<MoonspiderEntity> metabolismProperties) {
        metabolismProperties.setDiet(AnimalDietType.CARNIVORE, Diets.CARNIVORE);
        metabolismProperties.setMaxWater((int) JSUtils.toTicksMCDays(2F));
        metabolismProperties.setMaxFood((int) JSUtils.toTicksMCDays(1F));
        metabolismProperties.setStalkingSpeedMultiplier(0.7F);
    }

    @Override
    protected void applyMiscProperties(JSMiscProperties<MoonspiderEntity> miscProperties) {
        miscProperties.setEra(Era.QUATERNARY);
        miscProperties.setMaxHeadRotation(20, 46);
        miscProperties.setBabyAnimations();
        miscProperties.setGuidebookScaling(new float[]{ 1.2f,1.7f });
        miscProperties.setGuidebookOffset(new Vec2(-1,0),new Vec2(0,0));
        miscProperties.setExtinct();

        miscProperties.setGuideBookDescription("The Moonspider, or Galeodes luna, were a nocturnal species of venomous Solifugid. Dwelling mostly in the undergrowth of Skull Island, the moonspiders hunted primarily rodents and other small prey. Their venom is strong enough to cause significant discomfort to even an adult dinosaur.");
        miscProperties.setGuideBookSource("Skull Island");
        miscProperties.setGuideBookScientificName("Galeodes luna");

        miscProperties.setAdvancementTitle("Goodbye, Moonspiders");
        miscProperties.disableBabyGuidebook();
        miscProperties.setVersion(-1);
    }

    @Override
    protected void applySocialProperties(JSSocialGroupProperties<MoonspiderEntity> socialGroupProperties) {
        socialGroupProperties.addHuntTargets(
                //Vanilla
                AbstractVillager.class, Player.class, Monster.class,
                Animal.class,

                //V1
                DryosaurusEntity.class, GoatEntity.class,
                ProcompsognathusEntity.class, VelociraptorEntity.class,

                //V2
                MicroceratusEntity.class, CompsognathusEntity.class
        );
        socialGroupProperties.addScaredOf(
                TyrannosaurusEntity.class
        );
        socialGroupProperties.addHerdTargets(MoonspiderEntity.class);
        socialGroupProperties.setMaxDistanceToPackLeader(900);
        socialGroupProperties.setMinDistanceToPackLeader(400);
        socialGroupProperties.setMaxHerdSize(7);
        socialGroupProperties.setNaturalAggression(1F);
    }

    @Override
    protected void applyItemProperties(JSItemProperties<MoonspiderEntity> itemProperties) {
        itemProperties.setEggtype(EggType.SPIDER);
        itemProperties.setSpawnEggColors(0x584E47, 0x161312);
        itemProperties.disableMeat();
        itemProperties.setHasFossil(false);
    }

    @Override
    protected void applyTravelersProperties(EntityAttributeProperties<MoonspiderEntity> attributes, EntityBaseProperties<MoonspiderEntity> base) {
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
        base.setRenderScale(0.65F);
        base.setLocator(new ResourceLocator<MoonspiderEntity>() {
            private static final ResourceLocation texture = JSExpanded.createId("textures/geo/animal/moonspider/moonspider.png");
            private static final ResourceLocation model = JSExpanded.createId("geo/animal/moonspider/moonspider.geo.json");
            private static final ResourceLocation animation = JSExpanded.createId("animations/animal/moonspider/moonspider.animation.json");
            @Override
            public ResourceLocation getTextureLocation(MoonspiderEntity entity) {
                return texture;
            }

            @Override
            public ResourceLocation getModelLocation(MoonspiderEntity entity) {
                return model;
            }

            @Override
            public ResourceLocation getAnimationLocation(MoonspiderEntity entity) {
                return animation;
            }
        });

        /*
        Base Attributes
         */
        attributes.setPersistent();
        attributes.setWaterEfficiency(0.6F);
        attributes.setEntityFactory(MoonspiderEntity::new);
        attributes.setEyeHeight(0.95F);
        attributes.setCategory(MobCategory.CREATURE);
        attributes.setMaxHealth(15.0F);
        attributes.setDimensions(0.5F,0.5F);
        attributes.setTrackingRange(64);

        /*
        Speed Related
         */
        attributes.setMovementSpeed(JSUtils.kmhToSpeed(8));
        attributes.setWaterEfficiency(0.8F);
        attributes.setRunningSpeedMultiplier(2.7F);

        /*
        Combat Related
         */
        attributes.setAttackDamage(6F);
        attributes.setAttackSpeed(2F);
        attributes.setAttackKnockback(0.5F);
        attributes.setFollowRange(64.0F);
    }

    static class Animations extends JSAnimator<MoonspiderEntity> {
        @Override
        public void animate(MoonspiderEntity base, TravelersMoveAnalysis moveAnalysis, TravelersAnimalAnimationModule animationManager) {
            if(base.isDead()) {
                JSAnimations.DEATH.sendForEntity(base);
                return;
            }

            if (isMoving(moveAnalysis)) {
                if(base.isRunning()) {
                    JSAnimations.RUN.sendForEntity(base);
                } else {
                    JSAnimations.WALK.sendForEntity(base);
                }
            } else {
                JSAnimations.IDLE.sendForEntity(base);
            }
        }

        @Override
        public void animateServer(MoonspiderEntity base, TravelersMoveAnalysis moveAnalysis, TravelersAnimalAnimationModule animationManager) {
            if(base.isDead()) return;
            if (base.curAttackTicks > 0) {
                JSAnimations.ATTACK.sendForEntity(base);
                return;
            }
            if (base.curInjuredTicks > 0) {
                JSAnimations.INJURED.sendForEntity(base);
                return;
            }
        }
    }
}
