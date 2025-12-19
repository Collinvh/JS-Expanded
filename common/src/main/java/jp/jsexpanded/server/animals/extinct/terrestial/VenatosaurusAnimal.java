package jp.jsexpanded.server.animals.extinct.terrestial;

import jp.jsexpanded.server.animals.AbstractAddonAnimal;
import jp.jsexpanded.server.animals.JSExpandedLocator;
import jp.jsexpanded.server.animals.entity.extinct.terrestial.venatosaurus.VenatosaurusEntity;
import jp.jurassicsaga.server.base.animal.entity.obj.diet.Diets;
import jp.jurassicsaga.server.base.animal.entity.obj.info.AnimalDietType;
import jp.jurassicsaga.server.base.animal.obj.attributes.*;
import jp.jurassicsaga.server.base.generic.gene.JSGenetics;
import jp.jurassicsaga.server.base.generic.obj.ActiveTime;
import jp.jurassicsaga.server.base.generic.obj.EggType;
import jp.jurassicsaga.server.base.generic.obj.Era;
import jp.jurassicsaga.server.base.generic.util.JSUtils;
import jp.jurassicsaga.server.v1.animal.animations.extinct.terrestial.VelociraptorAnimations;
import jp.jurassicsaga.server.v1.animal.entity.extant.GoatEntity;
import jp.jurassicsaga.server.v1.animal.entity.extant.OstrichEntity;
import jp.jurassicsaga.server.v1.animal.entity.extinct.terrestial.*;
import jp.jurassicsaga.server.v2.animal.entity.extinct.terrestial.*;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import travelers.server.animal.obj.attributes.EntityAttributeProperties;
import travelers.server.animal.obj.attributes.EntityBaseProperties;
import travelers.server.animal.obj.misc.AnimalType;

public class VenatosaurusAnimal extends AbstractAddonAnimal<VenatosaurusEntity> {
    public VenatosaurusAnimal() {
        super("venatosaurus");
        setAnimator(new VelociraptorAnimations());
    }

    @Override
    protected void applyGeneticProperties(JSGeneticProperties<VenatosaurusEntity> geneticProperties) {
        geneticProperties.addGrowthStageScaling(0.4F);
        geneticProperties.addGrowthStageSize(0.5f, 0.7f);
        geneticProperties.addGrowthNextStageScaling(1.1F);
        geneticProperties.addGrowthStageMaxGrowth((int) JSUtils.toTicksMCDays(4));
        geneticProperties.setGrowthProgressCap((int) JSUtils.toTicksMCDays(6));
        geneticProperties.addSupportedGenes(JSGenetics.FOREST_COSMETIC);
    }

    @Override
    protected void applyMetabolismProperties(JSMetabolismProperties<VenatosaurusEntity> metabolismProperties) {
        metabolismProperties.setDiet(AnimalDietType.CARNIVORE, Diets.CARNIVORE);
        metabolismProperties.disableWater();
        metabolismProperties.setMaxFood((int) JSUtils.toTicksMCDays(1F));
        metabolismProperties.setStalkingSpeedMultiplier(0.7F);
    }

    @Override
    protected void applyMiscProperties(JSMiscProperties<VenatosaurusEntity> miscProperties) {
        miscProperties.setEra(Era.QUATERNARY);
        miscProperties.setMaxHeadRotation(20, 46);
        miscProperties.setBabyAnimations();
        miscProperties.setGuidebookScaling(new float[]{ 1.2f,0.89f });
        miscProperties.setGuidebookOffset(new Vec2(-2,0),new Vec2(0,0));
        miscProperties.setExtinct();

        miscProperties.setGuideBookDescription("???");
        miscProperties.setGuideBookSource("Skull Island");
        miscProperties.setGuideBookScientificName("Venatosaurus");

        miscProperties.setAdvancementTitle("???");
        miscProperties.setVersion(-1);
    }

    @Override
    protected void applySocialProperties(JSSocialGroupProperties<VenatosaurusEntity> socialGroupProperties) {
        socialGroupProperties.addHuntTargets(
                //Vanilla
                AbstractVillager.class, Player.class, Monster.class,
                Animal.class,

                //V1
                DryosaurusEntity.class, GoatEntity.class, OstrichEntity.class,
                ParasaurolophusEntity.class, HadrosaurusEntity.class, ApatosaurusEntity.class,
                GallimimusEntity.class, ProcompsognathusEntity.class, VelociraptorEntity.class,

                //V2
                MetriacanthosaurusEntity.class, EuoplocephalusEntity.class, StyracosaurusEntity.class,
                MaiasauraEntity.class, MicroceratusEntity.class, AchillobatorEntity.class
        );
        socialGroupProperties.addScaredOf(
                TyrannosaurusEntity.class
        );
        socialGroupProperties.addHerdTargets(VenatosaurusEntity.class);
        socialGroupProperties.setMaxDistanceToPackLeader(900);
        socialGroupProperties.setMinDistanceToPackLeader(400);
        socialGroupProperties.setMaxHerdSize(7);
        socialGroupProperties.setNaturalAggression(1F);
    }

    @Override
    protected void applyItemProperties(JSItemProperties<VenatosaurusEntity> itemProperties) {
        itemProperties.setEggtype(EggType.ALLIGATOR);
        itemProperties.setSpawnEggColors(0x676D3F, 0x806134);
        itemProperties.setSpawnEggColorsMale(0x424B2A, 0x895315);
    }

    @Override
    protected void applyTravelersProperties(EntityAttributeProperties<VenatosaurusEntity> attributes, EntityBaseProperties<VenatosaurusEntity> base) {
        /*
        Base Properties
         */
        base.setAnimalType(AnimalType.REPTILE);
        base.setFov(90);
        base.setMaxTurnRate(9);
        base.setSizeDimorphism(9);
        base.setTurnSmoothRate(0.45F);

        /*
        Render Properties
         */
        base.setRenderScale(1.35F);
        base.setLocator(new JSExpandedLocator<>());
        base.setBabyAnimations();

        /*
        Base Attributes
         */
        attributes.setPersistent();
        attributes.setWaterEfficiency(0.6F);
        attributes.setEntityFactory(VenatosaurusEntity::new);
        attributes.setEyeHeight(0.95F);
        attributes.setCategory(MobCategory.CREATURE);
        attributes.setMaxHealth(90.0F);
        attributes.setDimensions(1.7F,2.9F);
        attributes.setTrackingRange(64);

        /*
        Speed Related
         */
        attributes.setMovementSpeed(JSUtils.kmhToSpeed(18));
        attributes.setWaterEfficiency(0.8F);
        attributes.setRunningSpeedMultiplier(2.25F);

        /*
        Combat Related
         */
        attributes.setAttackDamage(14F);
        attributes.setAttackSpeed(2F);
        attributes.setAttackKnockback(0.5F);
        attributes.setFollowRange(64.0F);
    }
}
