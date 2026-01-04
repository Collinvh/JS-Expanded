package jp.jsexpanded.server.animals;

import jp.jsexpanded.server.animals.entity.extinct.aquatic.PiranhadonEntity;
import jp.jsexpanded.server.animals.entity.extinct.terrestial.venatosaurus.VenatosaurusEntity;
import jp.jsexpanded.server.animals.extinct.aquatic.BloodfishAnimal;
import jp.jsexpanded.server.animals.extinct.aquatic.CunaepraedatorAnimal;
import jp.jsexpanded.server.animals.extinct.aquatic.PiranhadonAnimal;
import jp.jsexpanded.server.animals.extinct.avian.CelocimexAnimal;
import jp.jsexpanded.server.animals.extinct.avian.SpinaculexAnimal;
import jp.jsexpanded.server.animals.extinct.terrestial.MoonspiderAnimal;
import jp.jsexpanded.server.animals.extinct.aquatic.SicklefinAnimal;
import jp.jsexpanded.server.animals.extinct.terrestial.VenatosaurusAnimal;
import jp.jurassicsaga.server.base.animal.obj.JSAnimal;
import jp.jurassicsaga.server.v1.animal.JSV1Animals;
import jp.jurassicsaga.server.v2.animal.JSV2Animals;
import net.minecraft.world.entity.LivingEntity;

public class JSExpandedAnimals {
    public static VenatosaurusAnimal VENATOSAURUS;
    public static MoonspiderAnimal MOONSPIDER;
    public static SicklefinAnimal SICKLEFIN;
    public static CelocimexAnimal CELOCIMEX;
    public static BloodfishAnimal BLOODFISH;
    public static CunaepraedatorAnimal CUNAEPRAEDATOR;
    public static PiranhadonAnimal PIRANHADON;
    public static SpinaculexAnimal SPINACULEX;

    public static void init() {

        VENATOSAURUS = new VenatosaurusAnimal();
        MOONSPIDER = new MoonspiderAnimal();
        SICKLEFIN = new SicklefinAnimal();
        CELOCIMEX = new CelocimexAnimal();
        BLOODFISH = new BloodfishAnimal();
        PIRANHADON = new PiranhadonAnimal();
        CUNAEPRAEDATOR = new CunaepraedatorAnimal();
        SPINACULEX = new SpinaculexAnimal();
    }

    /***
    Don't add pigs ect in here this is just to
    invoke the JurassicSaga or other addon's animals
     */
    public static void finalizeAnimals() {
        initScared();
        initHunt();
        initHerd();
    }

    private static void initHunt() {
        huntMe(VenatosaurusEntity.class, JSV2Animals.BARYONYX, JSV1Animals.TYRANNOSAURUS, JSV1Animals.TYLOSAURUS);
        huntMe(PiranhadonEntity.class, JSV1Animals.TYLOSAURUS);
    }

    private static void initScared() {
        beScaredOfMe(VenatosaurusEntity.class,
                JSV1Animals.DRYOSAURUS, JSV1Animals.GOAT, JSV1Animals.OSTRICH,
                JSV1Animals.PARASAUROLOPHUS, JSV1Animals.HADROSAURUS,
                JSV1Animals.GALLIMIMUS, JSV1Animals.PROCOMPSOGNATHUS, JSV1Animals.VELOCIRAPTOR,

                JSV2Animals.METRIACANTHOSAURUS, JSV2Animals.ACHILLOBATOR, JSV2Animals.EUOPLOCEPHALUS,
                JSV2Animals.STYRACOSAURUS, JSV2Animals.MAIASAURA, JSV2Animals.MICROCERATUS
        );

        beScaredOfMe(PiranhadonEntity.class,
                JSV1Animals.BONITO
        );
    }

    private static void initHerd() {

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


    public static void herdWithMe(Class<? extends LivingEntity> meClazz, JSAnimal<?>... scaredAnimals) {
        for (JSAnimal<?> scaredAnimal : scaredAnimals) {
            scaredAnimal.getAnimalAttributes().getSocialGroupProperties().addHerdTargets(meClazz);
        }
    }
}
