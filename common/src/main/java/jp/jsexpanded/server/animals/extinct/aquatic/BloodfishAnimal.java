package jp.jsexpanded.server.animals.extinct.aquatic;

import jp.jsexpanded.server.animals.AbstractAddonAnimal;
import jp.jsexpanded.server.animals.JSExpandedLocator;
import jp.jsexpanded.server.animals.entity.extinct.aquatic.BloodfishEntity;
import jp.jsexpanded.server.animals.entity.extinct.aquatic.SicklefinEntity;
import jp.jsexpanded.server.animals.entity.extinct.terrestial.moonspider.MoonspiderEntity;
import jp.jurassicsaga.server.base.animal.animals.JSAnimations;
import jp.jurassicsaga.server.base.animal.animations.JSAnimator;
import jp.jurassicsaga.server.base.animal.entity.obj.diet.Diets;
import jp.jurassicsaga.server.base.animal.entity.obj.info.AnimalDietType;
import jp.jurassicsaga.server.base.animal.obj.attributes.*;
import jp.jurassicsaga.server.base.animal.obj.locator.JSAnimalBaseLocator;
import jp.jurassicsaga.server.base.generic.obj.EggType;
import jp.jurassicsaga.server.base.generic.obj.Era;
import jp.jurassicsaga.server.base.generic.util.JSUtils;
import jp.jurassicsaga.server.v1.animal.entity.extant.GoatEntity;
import jp.jurassicsaga.server.v1.animal.entity.extinct.terrestial.DryosaurusEntity;
import jp.jurassicsaga.server.v1.animal.entity.extinct.terrestial.ProcompsognathusEntity;
import jp.jurassicsaga.server.v1.animal.entity.extinct.terrestial.TyrannosaurusEntity;
import jp.jurassicsaga.server.v1.animal.entity.extinct.terrestial.VelociraptorEntity;
import jp.jurassicsaga.server.v2.animal.entity.extinct.terrestial.CompsognathusEntity;
import jp.jurassicsaga.server.v2.animal.entity.extinct.terrestial.MicroceratusEntity;
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
import travelers.server.animal.obj.misc.AnimalType;

public class BloodfishAnimal extends AbstractAddonAnimal<BloodfishEntity> {
    public BloodfishAnimal() {
        super("bloodfish");
        setAnimator(new Animations());
    }

    @Override
    protected void applyGeneticProperties(JSGeneticProperties<BloodfishEntity> geneticProperties) {
        geneticProperties.addGrowthStageScaling(0.4F);
        geneticProperties.addGrowthStageSize(0.5f, 0.7f);
        geneticProperties.addGrowthNextStageScaling(1.1F);
        geneticProperties.addGrowthStageMaxGrowth((int) JSUtils.toTicksMCDays(4));
        geneticProperties.setGrowthProgressCap((int) JSUtils.toTicksMCDays(6));
        geneticProperties.addSupportedGenes();
    }

    @Override
    protected void applyMetabolismProperties(JSMetabolismProperties<BloodfishEntity> metabolismProperties) {
        metabolismProperties.setDiet(AnimalDietType.CARNIVORE, Diets.CARNIVORE);
        metabolismProperties.setMaxWater((int) JSUtils.toTicksMCDays(2F));
        metabolismProperties.setMaxFood((int) JSUtils.toTicksMCDays(1F));
        metabolismProperties.setStalkingSpeedMultiplier(0.7F);
    }

    @Override
    protected void applyMiscProperties(JSMiscProperties<BloodfishEntity> miscProperties) {
        miscProperties.setEra(Era.QUATERNARY);
        miscProperties.setMaxHeadRotation(20, 46);
        miscProperties.setGuidebookScaling(new float[]{ 1.2f,1.7f });
        miscProperties.setGuidebookOffset(new Vec2(-1,0),new Vec2(0,0));
        miscProperties.setExtinct();

        miscProperties.setGuideBookDescription("The Bloodfish, or Sanguichthys rufus, was a cyprinid which inhabited Skull island's backwater canals and freshwater estuaries. Its bright red coloring was thought to provide it camouflage against the murky tannin stained water that was its preferred habitat.");
        miscProperties.setGuideBookSource("Skull Island");
        miscProperties.setGuideBookScientificName("Bloodfish");

        miscProperties.setAdvancementTitle("Let The Rivers Fill With Bloodfish");
        miscProperties.disableBabyGuidebook();
        miscProperties.setVersion(-1);
    }

    @Override
    protected void applySocialProperties(JSSocialGroupProperties<BloodfishEntity> socialGroupProperties) {
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
    protected void applyItemProperties(JSItemProperties<BloodfishEntity> itemProperties) {
        itemProperties.setEggtype(EggType.FISH);
        itemProperties.setSpawnEggColors(0xCD8656, 0xB42805);
        itemProperties.setSpawnEggColorsMale(0xE4613E, 0x992205);
        itemProperties.disableMeat();
        itemProperties.setHasFossil(false);
    }

    @Override
    protected void applyTravelersProperties(EntityAttributeProperties<BloodfishEntity> attributes, EntityBaseProperties<BloodfishEntity> base) {
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
        base.setRenderScale(0.25F);
        base.setLocator(new JSExpandedLocator<BloodfishEntity>().setAdultOnly());

        /*
        Base Attributes
         */
        attributes.setPersistent();
        attributes.setWaterEfficiency(0.6F);
        attributes.setEntityFactory(BloodfishEntity::new);
        attributes.setEyeHeight(0.95F);
        attributes.setCategory(MobCategory.CREATURE);
        attributes.setMaxHealth(15.0F);
        attributes.setDimensions(0.5F,0.5F);
        attributes.setTrackingRange(64);

        /*
        Speed Related
         */
        attributes.setMovementSpeed(JSUtils.kmhToSpeed(18));
        attributes.setWaterEfficiency(0.8F);
        attributes.setRunningSpeedMultiplier(1.45F);

        /*
        Combat Related
         */
        attributes.setAttackDamage(6F);
        attributes.setAttackSpeed(2F);
        attributes.setAttackKnockback(0.5F);
        attributes.setFollowRange(64.0F);
    }

    static class Animations extends JSAnimator<BloodfishEntity> {
        @Override
        public void animate(BloodfishEntity base, TravelersMoveAnalysis moveAnalysis, TravelersAnimalAnimationModule animationManager) {
            if (!base.isInWaterOrBubble()) {
                JSAnimations.FLOP.sendForEntity(base);
                return;
            }

            if (base.isInWater()) {
                if (base.getInBlockState().is(Blocks.BUBBLE_COLUMN)) {
                    JSAnimations.FLOP.sendForEntity(base);
                } else if (isMoving(moveAnalysis)) {
                    JSAnimations.SWIM.sendForEntity(base);
                } else {
                    JSAnimations.IDLE_IN_WATER.sendForEntity(base);
                }
            } else {
                JSAnimations.BEACHED.sendForEntity(base);
            }
        }
    }
}

