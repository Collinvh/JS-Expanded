package jp.jsexpanded.server.animals;

import jp.jsexpanded.server.animals.entity.extinct.terrestial.VenatosaurusEntity;
import jp.jsexpanded.server.animals.extinct.terrestial.VenatosaurusAnimal;
import jp.jurassicsaga.server.base.animal.obj.JSAnimal;
import jp.jurassicsaga.server.v1.animal.JSV1Animals;
import jp.jurassicsaga.server.v2.animal.JSV2Animals;
import net.minecraft.world.entity.LivingEntity;

public class JSExpandedAnimals {
    public static final VenatosaurusAnimal VENATOSAURUS = new VenatosaurusAnimal();

    public static void init() {
        beScaredOfMe(VenatosaurusEntity.class,
                JSV1Animals.DRYOSAURUS, JSV1Animals.GOAT, JSV1Animals.OSTRICH,
                JSV1Animals.PARASAUROLOPHUS, JSV1Animals.HADROSAURUS,
                JSV1Animals.GALLIMIMUS, JSV1Animals.PROCOMPSOGNATHUS, JSV1Animals.VELOCIRAPTOR,

                JSV2Animals.METRIACANTHOSAURUS, JSV2Animals.ACHILLOBATOR, JSV2Animals.EUOPLOCEPHALUS,
                JSV2Animals.STYRACOSAURUS, JSV2Animals.MAIASAURA, JSV2Animals.MICROCERATUS
        );

        huntMe(VenatosaurusEntity.class, JSV2Animals.BARYONYX, JSV1Animals.TYRANNOSAURUS, JSV1Animals.TYLOSAURUS);
    }


    public static void beScaredOfMe(Class<? extends LivingEntity> meClazz, JSAnimal<?>... scaredAnimals) {
        for (JSAnimal<?> scaredAnimal : scaredAnimals) {
            scaredAnimal.getAnimalAttributes().getSocialGroupProperties().addScaredOf(meClazz);
        }
    }


    public static void huntMe(Class<? extends LivingEntity> meClazz, JSAnimal<?>... scaredAnimals) {
        for (JSAnimal<?> scaredAnimal : scaredAnimals) {
            scaredAnimal.getAnimalAttributes().getSocialGroupProperties().addHuntTargets(meClazz);
        }
    }


    public static void herdWithme(Class<? extends LivingEntity> meClazz, JSAnimal<?>... scaredAnimals) {
        for (JSAnimal<?> scaredAnimal : scaredAnimals) {
            scaredAnimal.getAnimalAttributes().getSocialGroupProperties().addHerdTargets(meClazz);
        }
    }
}
