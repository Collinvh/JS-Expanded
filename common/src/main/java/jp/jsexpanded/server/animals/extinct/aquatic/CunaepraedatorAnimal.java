package jp.jsexpanded.server.animals.extinct.aquatic;

import jp.jsexpanded.JSExpanded;
import jp.jsexpanded.server.animals.AbstractAddonAnimal;
import jp.jsexpanded.server.animals.JSExpandedLocator;
import jp.jsexpanded.server.animals.entity.extinct.aquatic.cunaepraedator.CunaepraedatorEntity;
import jp.jsexpanded.server.animals.entity.extinct.terrestial.moonspider.MoonspiderEntity;
import jp.jurassicsaga.JSCommon;
import jp.jurassicsaga.server.base.animal.animals.JSAnimations;
import jp.jurassicsaga.server.base.animal.animations.JSAnimator;
import jp.jurassicsaga.server.base.animal.entity.obj.diet.Diets;
import jp.jurassicsaga.server.base.animal.entity.obj.info.AnimalDietType;
import jp.jurassicsaga.server.base.animal.entity.obj.other.JSVariants;
import jp.jurassicsaga.server.base.animal.obj.attributes.*;
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
import travelers.server.animal.obj.misc.AnimalType;

import java.util.concurrent.ExecutionException;

public class CunaepraedatorAnimal extends AbstractAddonAnimal<CunaepraedatorEntity> {
    public CunaepraedatorAnimal() {
        super("cunaepraedator");
        setAnimator(new Animations());
    }

    @Override
    protected void applyGeneticProperties(JSGeneticProperties<CunaepraedatorEntity> geneticProperties) {
        geneticProperties.addGrowthStageScaling(0.4F);
        geneticProperties.addGrowthStageSize(0.5f, 0.7f);
        geneticProperties.addGrowthNextStageScaling(0.65F);
        geneticProperties.addGrowthStageMaxGrowth((int) JSUtils.toTicksMCDays(4));
        geneticProperties.setGrowthProgressCap((int) JSUtils.toTicksMCDays(6));
        geneticProperties.addSupportedGenes();
    }

    @Override
    protected void applyMetabolismProperties(JSMetabolismProperties<CunaepraedatorEntity> metabolismProperties) {
        metabolismProperties.setDiet(AnimalDietType.CARNIVORE, Diets.CARNIVORE);
        metabolismProperties.setMaxWater((int) JSUtils.toTicksMCDays(2F));
        metabolismProperties.setMaxFood((int) JSUtils.toTicksMCDays(1F));
        metabolismProperties.setStalkingSpeedMultiplier(0.7F);
    }

    @Override
    protected void applyMiscProperties(JSMiscProperties<CunaepraedatorEntity> miscProperties) {
        miscProperties.setEra(Era.QUATERNARY);
        miscProperties.setMaxHeadRotation(20, 46);
        miscProperties.setGuidebookScaling(new float[]{ 1.2f,1.7f });
        miscProperties.setGuidebookOffset(new Vec2(-1,0),new Vec2(-0.25F,0));
        miscProperties.setExtinct();
        miscProperties.disableBabyGuidebook();

        miscProperties.setGuideBookDescription("N/A");
        miscProperties.setGuideBookSource("Skull Island");
        miscProperties.setGuideBookScientificName("N/A");

        miscProperties.setAdvancementTitle("N/A");
        miscProperties.setVersion(JSExpanded.EXPANDED_VERSION);
    }

    @Override
    protected void applySocialProperties(JSSocialGroupProperties<CunaepraedatorEntity> socialGroupProperties) {
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
        socialGroupProperties.addHerdTargets(CunaepraedatorEntity.class);
        socialGroupProperties.setMaxDistanceToPackLeader(900);
        socialGroupProperties.setMinDistanceToPackLeader(400);
        socialGroupProperties.setMaxHerdSize(7);
        socialGroupProperties.setNaturalAggression(1F);
    }

    @Override
    protected void applyItemProperties(JSItemProperties<CunaepraedatorEntity> itemProperties) {
        itemProperties.setEggtype(EggType.SPIDER);
        itemProperties.setSpawnEggColors(0x696358, 0x51442D);
        itemProperties.disableMeat();
        itemProperties.setHasFossil(false);
    }

    @Override
    protected void applyTravelersProperties(EntityAttributeProperties<CunaepraedatorEntity> attributes, EntityBaseProperties<CunaepraedatorEntity> base) {
        /*
        Base Properties
         */
        base.setAnimalType(AnimalType.INSECT);
        base.setFov(90);
        base.setMaxTurnRate(9);
        base.setTurnSmoothRate(0.7F);

        /*
        Render Properties
         */
        base.setRenderScale(1.0F);
        base.setSizeDimorphism(600);
        base.setLocator(new JSExpandedLocator<>() {
            @Override
            public ResourceLocation getModelLocation(CunaepraedatorEntity entity) {
                String key = makeCacheKey(entity, "model");
                try {
                    return cache.get(key, () -> {
                        String entity_name = getEntityName(entity);
                        return JSExpanded.createId("geo/animal/" + entity_name + "/" + entity_name + ".geo.json");
                    });
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }


            public ResourceLocation getTextureLocation(CunaepraedatorEntity entity) {
                String key = makeCacheKey(entity, "texture");

                try {
                    return (ResourceLocation)this.cache.get(key, () -> this.buildTexture(entity));
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }

            private ResourceLocation buildTexture(CunaepraedatorEntity entity) {
                String name = this.getEntityName(entity);
                JSVariants.JSVariant variant = entity.getModules().getGeneticModule().getGeneData().getVariant();
                boolean extinct = entity.getAnimal().getAnimalAttributes().getMiscProperties().isExtinct();
                if (variant != JSVariants.NONE && extinct) {
                    String basePath = "textures/geo/animal/" + name + "/variants/" + name + "_" + variant.getName();
                    return JSExpanded.createId(basePath + ".png");
                } else {
                    return JSExpanded.createId("textures/geo/animal/" + name + "/" + name + ".png");
                }
            }
        });

        /*
        Base Attributes
         */
        attributes.setPersistent();
        attributes.setWaterEfficiency(0.6F);
        attributes.setEntityFactory(CunaepraedatorEntity::new);
        attributes.setEyeHeight(0.95F);
        attributes.setCategory(MobCategory.CREATURE);
        attributes.setMaxHealth(15.0F);
        attributes.setDimensions(1.75F,3F);
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

    static class Animations extends JSAnimator<CunaepraedatorEntity> {
        @Override
        public void animate(CunaepraedatorEntity base, TravelersMoveAnalysis moveAnalysis, TravelersAnimalAnimationModule animationManager) {
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
        public void animateServer(CunaepraedatorEntity base, TravelersMoveAnalysis moveAnalysis, TravelersAnimalAnimationModule animationManager) {
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
